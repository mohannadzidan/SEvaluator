package sevaluator.builtin.operations;

import sevaluator.Evaluable;
import sevaluator.Operation;

public final class Subtraction extends Operation {
    public Subtraction() {
        super('-', 0);
    }

    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return lhs.evaluate() - rhs.evaluate();
    }
}
