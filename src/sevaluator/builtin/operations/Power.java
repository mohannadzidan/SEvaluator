package sevaluator.builtin.operations;

import sevaluator.Evaluable;
import sevaluator.Operation;

public final class Power extends Operation {

    public Power() {
        super('^', 3);
    }

    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return Math.pow(lhs.evaluate(), rhs.evaluate());
    }

}
