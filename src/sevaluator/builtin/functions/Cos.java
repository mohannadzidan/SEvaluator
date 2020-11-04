package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;

public class Cos extends Function {

    public Cos() {
        super("cos");
    }

    @Override
    public double apply(Evaluable value) {
        return Math.cos(value.evaluate());
    }
}
