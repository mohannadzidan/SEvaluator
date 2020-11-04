package sevaluator;


public interface Operation extends ExpressionComponent {

    //        if (Character.isLetter(operator) || Character.isDigit(operator) || operator == ' ')
//            throw new RuntimeException("Illegal operator, the operators can only be special characters!");
    double apply(Evaluable lhs, Evaluable rhs);

    char getOperator();

    int getPriority();
}
