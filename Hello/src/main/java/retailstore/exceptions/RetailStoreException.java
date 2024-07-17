package retailstore.exceptions;

public class RetailStoreException extends RuntimeException {

    public RetailStoreException(String message) {
        super(message);
    }

    public RetailStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
