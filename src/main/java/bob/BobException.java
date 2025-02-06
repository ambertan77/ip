package bob;

// code adapted from https://www.baeldung.com/java-new-custom-exception

/**
 * Represents the custom exception class created for Bob. If a BobException is thrown,
 * an error, with details specified in the error message, has occurred when running Bob.
 */
public class BobException extends Exception {
    /**
     * Constructs a new instance of BobException with a custom error message.
     *
     * @param message A String containing details of the error that has occurred.
     */
    public BobException(String message) {
        super(message);
    }
}
