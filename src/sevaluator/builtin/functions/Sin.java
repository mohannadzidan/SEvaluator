package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;


public final class Sin implements Function {
    @Override
    public double apply(Evaluable value) {
        return Math.sin(value.evaluate());
    }

    @Override
    public String getKeyword() {
        return "sin";
    }
}
