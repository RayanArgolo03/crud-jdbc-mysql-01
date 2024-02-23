package exceptions;

import java.io.Serial;

public final class DbConnectionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1197976454952968103L;

    public DbConnectionException(String message) {
        super(message);
    }
}
