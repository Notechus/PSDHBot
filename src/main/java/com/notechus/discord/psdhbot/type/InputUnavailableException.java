package com.notechus.discord.psdhbot.type;

/**
 * @author notechus.
 */
public class InputUnavailableException extends RuntimeException {

    protected static final String MESSAGE = "Could not find specified input";

    public InputUnavailableException() {
        super(MESSAGE);
    }

    public InputUnavailableException(String message) {
        super(MESSAGE + ":" + message);
    }

    public InputUnavailableException(String message, Throwable cause) {
        super(MESSAGE + ":" + message, cause);
    }

    public InputUnavailableException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public InputUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(MESSAGE + ":" + message, cause, enableSuppression, writableStackTrace);
    }
}
