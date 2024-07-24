package org.example.scoreboard.exception;

/**
 * Exception thrown when a game cannot be found.
 */
public class GameNotFoundException extends RuntimeException {
    /**
     * Constructs a new GameNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     * @param args    the arguments to be formatted into the message.
     */
    public GameNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
