package lk.ijse.carhire.dao.custom.exception;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException() {
        super();
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}

