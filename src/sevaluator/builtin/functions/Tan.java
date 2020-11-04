package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;

public class Tan extends Function {

    public Tan() {
        super("tan");
    }

    @Override
    public double apply(Evaluable value) {
        return Math.tan(value.evaluate());
    }
}
