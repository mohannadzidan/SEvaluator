package sevaluator.builtin.operations;

import sevaluator.Evaluable;
import sevaluator.Operation;

public class Division extends Operation {
    public Division() {
        super('/', 2);
    }


    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return lhs.evaluate() / rhs.evaluate();
    }
}
