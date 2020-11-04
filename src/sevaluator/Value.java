package sevaluator;

class Value extends ExpressionComponent implements Evaluable {

    private double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
