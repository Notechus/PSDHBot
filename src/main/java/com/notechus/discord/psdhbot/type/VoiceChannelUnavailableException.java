package com.notechus.discord.psdhbot.type;

/**
 * @author notechus.
 */
public class VoiceChannelUnavailableException extends RuntimeException {
    protected static final String MESSAGE = "Specified voice channel is unavailable";

    public VoiceChannelUnavailableException() {
        super(MESSAGE);
    }

    public VoiceChannelUnavailableException(String message) {
        super(MESSAGE + ":" + message);
    }

    public VoiceChannelUnavailableException(String message, Throwable cause) {
        super(MESSAGE + ":" + message, cause);
    }

    public VoiceChannelUnavailableException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public VoiceChannelUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(MESSAGE + "\n" + message, cause, enableSuppression, writableStackTrace);
    }
}
