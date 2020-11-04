package sevaluator;

import sevaluator.builtin.functions.Cos;
import sevaluator.builtin.functions.Sin;
import sevaluator.builtin.functions.Tan;
import sevaluator.builtin.operations.*;
import sevaluator.exceptions.IllegalFormatException;

import java.util.ArrayList;
import java.util.Arrays;

public class Evaluator {

    private final static Function[] builtinFunctions = {
            new Sin(),
            new Cos(),
            new Tan()
    };
    private final static Operation[] builtinOperations = {
            new Addition(),
            new Subtraction(),
            new Multiplication(),
            new Division(),
            new Power()

    };
    private ArrayList<Operation> operations = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();
    /*****************************************************************
     ************************* public API ****************************
     *****************************************************************/
    public Evaluator(Initialization initializationType) {
        if (initializationType == Initialization.FUNCTIONS_ONLY || initializationType == Initialization.ALL)
            functions.addAll(Arrays.asList(builtinFunctions));
        if (initializationType == Initialization.OPERATIONS_ONLY || initializationType == Initialization.ALL)
            operations.addAll(Arrays.asList(builtinOperations));
    }

    Operation getOperation(char operator) {
        for (Operation op : operations) {
            if (op.getOperator() == operator) return op;
        }
        return null;
    }

    Function getFunction(String keyword) {
        for (Function f : functions) {
            if (f.getKeyword().equals(keyword)) return f;
        }
        return null;
    }

    public void addFunction(Function function) {
        // check
        char[] chars = function.getKeyword().toCharArray();
        for (char c : chars)
            if (!Character.isLetter(c))
                throw new IllegalFormatException("function keywords can only consist of letters!");
        functions.add(function);
    }

    public void addOperation(Operation operation) {
        // check
        char op = operation.getOperator();
        if (Character.isLetter(op) || Character.isDigit(op))
            throw new IllegalFormatException("the operators can only be special characters!");
        operations.add(operation);
    }

    public double evaluate(String infix) {
        if (infix == null) return 0;
        infix = infix.replace(" ", "");
        if (infix.isEmpty()) return 0;
        Expression e = new Expression();
        Expression.pack(e, this, infix, 0, infix.length() - 1);
        return e.evaluate();
    }


    public enum Initialization {ALL, OPERATIONS_ONLY, FUNCTIONS_ONLY, NONE}
}
