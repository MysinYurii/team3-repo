package com.team3chat.exceptions;

/**
 * Created by Java_12 on 07.09.2017.
 */
public class SavingHistoryException extends Exception {
    public SavingHistoryException() {
        super();
    }

    public SavingHistoryException(String message) {
        super(message);
    }

    public SavingHistoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavingHistoryException(Throwable cause) {
        super(cause);
    }

    protected SavingHistoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
