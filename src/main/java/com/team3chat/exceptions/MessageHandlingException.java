package com.team3chat.exceptions;

/**
 * Created by Java_12 on 08.09.2017.
 */
public class MessageHandlingException extends Exception {
    public MessageHandlingException() {
    }

    public MessageHandlingException(String message) {
        super(message);
    }

    public MessageHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageHandlingException(Throwable cause) {
        super(cause);
    }

    public MessageHandlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
