package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;

public final class Cos implements Function {

    @Override
    public double apply(Evaluable value) {
        return Math.cos(value.evaluate());
    }

    @Override
    public String getKeyword() {
        return "cos";
    }
}
