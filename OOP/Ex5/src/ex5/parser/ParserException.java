package ex5.parser;

/**
 * Custom exception to indicate parsing errors in the Sjavac parser.
 */
public class ParserException extends Exception {
    /**
     * Constructs a new ParserException with the specified detail message.
     *
     * @param message The detail message
     */
    public ParserException(String message) {
        super(message);
    }
}
