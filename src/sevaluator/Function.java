package sevaluator;


public interface Function extends ExpressionComponent {

    double apply(Evaluable value);

    String getKeyword();

    @Override
    String toString();
}
