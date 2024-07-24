package org.example.scoreboard.model.dto;

import org.example.scoreboard.model.valueobject.Score;
import org.example.scoreboard.util.ValidationUtils;

/**
 * Represents a team with its details.
 * It includes information about the team's name and score.
 */
public record TeamDto(
        String name,
        Score score
) {
    public TeamDto {
        ValidationUtils.requireNonNull(name, score);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
    }
}
