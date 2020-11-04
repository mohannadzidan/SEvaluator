package sevaluator.builtin.operations;

import sevaluator.Evaluable;
import sevaluator.Operation;

public class Multiplication extends Operation {
    public Multiplication() {
        super('*', 1);
    }

    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return lhs.evaluate() * rhs.evaluate();
    }
}
