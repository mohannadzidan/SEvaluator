package sevaluator;


public abstract class Function extends ExpressionComponent {
    private String keyword;

    public Function(String keyword) {
        for (char c : keyword.toCharArray()) {
            if (!Character.isLetter(c))
                throw new RuntimeException("Illegal custom function name!");
        }
        this.keyword = keyword;
    }

    public abstract double apply(Evaluable value);

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
