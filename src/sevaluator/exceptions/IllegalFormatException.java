package sevaluator.exceptions;

public class IllegalFormatException extends RuntimeException {
    public IllegalFormatException(String message) {
        super("Illegal format:" + message);
    }

    public IllegalFormatException() {
        super("Illegal format!");
    }
}
