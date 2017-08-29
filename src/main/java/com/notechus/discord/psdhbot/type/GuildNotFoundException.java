package com.notechus.discord.psdhbot.type;

/**
 * @author notechus.
 */
public class GuildNotFoundException extends RuntimeException {

    protected static final String MESSAGE = "Could not find the requested guild";

    public GuildNotFoundException() {
        super(MESSAGE);
    }

    public GuildNotFoundException(String message) {
        super(MESSAGE + ":" + message);
    }

    public GuildNotFoundException(String message, Throwable cause) {
        super(MESSAGE + ":" + message, cause);
    }

    public GuildNotFoundException(Throwable cause) {
        super(cause);
    }

    public GuildNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(MESSAGE + ":" + message, cause, enableSuppression, writableStackTrace);
    }
}
