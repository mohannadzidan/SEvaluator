package sevaluator;

class Value implements Evaluable {

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
