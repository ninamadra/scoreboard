package org.example.scoreboard.model.dto;

import org.example.scoreboard.util.ValidationUtils;

import java.util.UUID;

/**
 * Represents a game with its details.
 * It includes information about the game's ID, the home and away {@link TeamDto}.
 */
public record GameDto(
        UUID id,
        TeamDto homeTeam,
        TeamDto awayTeam
) {
    public GameDto {
        ValidationUtils.requireNonNull(id, homeTeam, awayTeam);
    }
}
