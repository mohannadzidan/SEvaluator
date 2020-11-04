package sevaluator.exceptions;

public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(String msg) {
        super("Syntax Error: " + msg);
    }

    public SyntaxErrorException() {
        super("Syntax Error!");
    }
}
