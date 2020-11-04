package sevaluator.builtin.operations;

import sevaluator.Evaluable;
import sevaluator.Operation;

public final class Addition implements Operation {


    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return lhs.evaluate() + rhs.evaluate();
    }

    @Override
    public char getOperator() {
        return '+';
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
