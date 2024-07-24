package org.example.scoreboard.model.valueobject;

import org.example.scoreboard.util.ValidationUtils;

/**
 * Represents a score value object.
 * Ensures that the score is non-negative.
 */
public record Score(int value) {
    public Score {
        ValidationUtils.requireNonNegative(value);
    }
}
