package sevaluator.builtin.functions;

import sevaluator.Evaluable;
import sevaluator.Function;

public class Sin extends Function {

    public Sin() {
        super("sin");
    }

    @Override
    public double apply(Evaluable value) {
        return Math.sin(value.evaluate());
    }
}
