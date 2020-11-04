package sevaluator;

import sevaluator.exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.Collections;

class Expression implements Evaluable {
    private ArrayList<ExpressionComponent> components = new ArrayList<>();

    public static int pack(Expression expression, Evaluator evaluator, String infix, int startIndex, int endIndex) {
        return pack(expression, evaluator, infix, startIndex, endIndex, false);
    }

    public static int pack(Expression expression, Evaluator evaluator, String infix, int startIndex, int endIndex, boolean expectClosing) {
        expression.components.clear();
        StringBuilder builder = new StringBuilder();
        boolean isPackingValue = false, isPackingFunction = false;
        int index = startIndex;
        for (; index <= endIndex; index++) {
            char c = infix.charAt(index);
            boolean isDigit, isLetter, isOperator;
            Operation op;
            isDigit = Character.isDigit((c)) || c == '.';
            isLetter = Character.isLetter(c);
            isOperator = (op = evaluator.getOperation(c)) != null;
            if (isPackingValue && !isDigit) {
                // pack value
                isPackingValue = false;
                expression.components.add(new Value(Double.parseDouble(builder.toString())));
                builder.setLength(0);
            }
            if (isPackingFunction && !isLetter) {
                // pack function
                isPackingFunction = false;
                Function f = evaluator.getFunction(builder.toString());
                if (f == null) throw new SyntaxErrorException("at " + index);
                expression.components.add(f);
                builder.setLength(0);
            }
            if (isDigit) {
                isPackingValue = true;
                builder.append(c);
                continue;
            } else if (isOperator) {
                expression.components.add(op);
                continue;
            } else if (isLetter) {
                isPackingFunction = true;
                builder.append(c);
            } else {
                if (c == '(') {
                    Expression e = new Expression();
                    index = pack(e, evaluator, infix, index + 1, endIndex, true);
                    expression.components.add(e);
                    builder.setLength(0);
                } else if (c == ')') {
                    break;
                }
            }


        }
        if (isPackingValue) {
            expression.components.add(new Value(Double.parseDouble(builder.toString())));
        } else if (isPackingFunction) {
            throw new SyntaxErrorException("at " + index);
        } else if (expectClosing && index < infix.length() && infix.charAt(index) != ')') {
            throw new SyntaxErrorException("expecting ')' at " + index);
        } else if (!expectClosing && index < infix.length() && infix.charAt(index) == ')') {
            throw new SyntaxErrorException("not expecting ')' at " + index);

        }
        return index;
    }

    @Override
    public double evaluate() {
        if (components.size() == 0) return 0;
        ArrayList<ExpressionComponent> components = new ArrayList<>(this.components);
        // get operation priorities
        ArrayList<Integer> priorities = new ArrayList<>();
        for (ExpressionComponent component : components) {
            if (component instanceof Operation) {
                Operation o = (Operation) component;
                if (!priorities.contains(o.getPriority())) priorities.add(o.getPriority());
            }
        }
        Collections.sort(priorities);
        // apply functions
        for (int i = 0; i < components.size(); i++) {
            ExpressionComponent c = components.get(i);
            if (c instanceof Function) {
                Function f = (Function) c;
                if (i == components.size() - 1) throw new SyntaxErrorException();
                ExpressionComponent nextC;
                if ((nextC = components.get(i + 1)) instanceof Evaluable) {
                    components.remove(i);
                    components.set(i, new Value(f.apply((Evaluable) nextC)));
                } else throw new SyntaxErrorException();
                i++;
            }
        }
        // the rest with priorities
        for (int a = priorities.size() - 1; a >= 0; a--) {
            int priority = priorities.get(a);
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof Operation) {
                    Operation op = (Operation) components.get(i);
                    if (op.getPriority() != priority) continue;
                    if (i == 0 || i == components.size() - 1) throw new SyntaxErrorException();
                    if (components.get(i + 1) instanceof Operation || components.get(i - 1) instanceof Operation)
                        throw new SyntaxErrorException();
                    Evaluable lhs = (Evaluable) components.get(i - 1);
                    Evaluable rhs = (Evaluable) components.get(i + 1);
                    Value value = new Value(op.apply(lhs, rhs));
                    i--;
                    components.remove(i); // lhs
                    components.remove(i); // operation
                    components.remove(i); // rhs
                    components.add(i, value);
                }
            }

        }
        if (components.size() != 1 || !(components.get(0) instanceof Evaluable)) {

            throw new SyntaxErrorException("remaining=" + components.size() + " = " + components.get(0).toString());
        }
        return ((Evaluable) components.get(0)).evaluate();
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder(50);
        value.append("(");
        for (ExpressionComponent ec : components) {
            if (ec instanceof Function)
                value.append(((Function) ec).getKeyword());
            else if (ec instanceof Operation)
                value.append(((Operation) ec).getOperator());
            else
                value.append(ec.toString());
        }
        value.append(")");
        return value.toString();
    }
}
