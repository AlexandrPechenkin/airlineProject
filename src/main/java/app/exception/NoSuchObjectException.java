package app.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class NoSuchObjectException extends DataIntegrityViolationException {

    public NoSuchObjectException(String msg) {
        super(msg);
    }
}

