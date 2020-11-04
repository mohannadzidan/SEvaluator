package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;

public final class Tan implements Function {

    @Override
    public double apply(Evaluable value) {
        return Math.tan(value.evaluate());
    }

    @Override
    public String getKeyword() {
        return "tan";
    }
}
