package ex5.main;

/**
 * Custom exception to indicate I/O errors in the Sjavac verifier.
 */
public class IOErrorException extends Exception{
    /**
     * Constructs a new IOErrorException with the specified detail message.
     *
     * @param message The detail message
     */
    public IOErrorException(String message) {
        super(message);
    }
}
