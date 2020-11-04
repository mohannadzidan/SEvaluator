package sevaluator;

import sevaluator.builtin.functions.Cos;
import sevaluator.builtin.functions.Sin;
import sevaluator.builtin.functions.Tan;
import sevaluator.builtin.operations.*;

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
    private Operation[] operations;
    private Function[] functions;


    //////////////////////
// public API
//////////////////////
    public Evaluator() {
        operations = new Operation[builtinOperations.length];
        functions = new Function[builtinFunctions.length];
        initBuiltin();
    }

    public Evaluator(Operation[] customOperations) {
        operations = new Operation[builtinOperations.length + customOperations.length];
        functions = new Function[builtinFunctions.length];
        initBuiltin();
        System.arraycopy(customOperations, 0, operations, builtinOperations.length, customOperations.length);
    }

    public Evaluator(Function[] customFunctions) {
        operations = new Operation[builtinOperations.length];
        functions = new Function[builtinFunctions.length + customFunctions.length];
        initBuiltin();
        System.arraycopy(customFunctions, 0, functions, builtinFunctions.length, customFunctions.length);
    }

    public Evaluator(Operation[] customOperations, Function[] customFunctions) {
        operations = new Operation[builtinOperations.length + customOperations.length];
        functions = new Function[builtinFunctions.length + customFunctions.length];
        initBuiltin();
        System.arraycopy(customOperations, 0, operations, builtinOperations.length, customOperations.length);
        System.arraycopy(customFunctions, 0, functions, builtinFunctions.length, customFunctions.length);

    }

    private void initBuiltin() {
        System.arraycopy(builtinOperations, 0, operations, 0, builtinOperations.length);
        System.arraycopy(builtinFunctions, 0, functions, 0, builtinFunctions.length);
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

    public double evaluate(String infix) {
        if (infix == null) return 0;
        infix = infix.replace(" ", "");
        if (infix.isEmpty()) return 0;
        Expression e = new Expression();
        Expression.pack(e, this, infix, 0, infix.length() - 1);
        return e.evaluate();
    }
}
