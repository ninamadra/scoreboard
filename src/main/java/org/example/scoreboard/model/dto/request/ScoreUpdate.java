package org.example.scoreboard.model.dto.request;

import org.example.scoreboard.util.ValidationUtils;

/**
 * Represents an update to the scores of a game.
 * Ensures that both home and away team scores are non-null and non-negative.
 */
public record ScoreUpdate(Integer homeTeamScore, Integer awayTeamScore) {
    public ScoreUpdate {
        ValidationUtils.requireNonNull(homeTeamScore, awayTeamScore);
        ValidationUtils.requireNonNegative(homeTeamScore, awayTeamScore);
    }
}
