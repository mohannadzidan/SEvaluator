package sevaluator;


public abstract class Operation extends ExpressionComponent {
    private char operator;
    private int priority;

    protected Operation(char operator, int priority) {
        if (Character.isLetter(operator) || Character.isDigit(operator))
            throw new RuntimeException("Illegal custom operator symbol!");
        this.operator = operator;
        this.priority = priority;
    }

    public abstract double apply(Evaluable lhs, Evaluable rhs);


    public char getOperator() {
        return operator;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return Character.toString(operator);
    }
}
