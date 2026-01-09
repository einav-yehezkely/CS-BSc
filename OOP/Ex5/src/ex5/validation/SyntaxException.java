package ex5.validation;

/**
 * Custom exception to indicate syntax errors in Sjava source files.
 */
public class SyntaxException extends Exception {
    /**
     * Constructs a new SyntaxException with the specified detail message.
     *
     * @param message The detail message
     */
    public SyntaxException(String message) {
        super(message);
    }
}
