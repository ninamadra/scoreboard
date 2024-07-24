package org.example.scoreboard.model.dto;

import java.util.Collection;
import java.util.Objects;

/**
 * Encapsulates a summary of ongoing games, represented as a collection of {@link GameDto} objects.
 * The games are ordered by their total score, with games having the same total score further ordered
 * by the most recently started match.
 */
public record GamesSummary(
        Collection<GameDto> games
) {
    public GamesSummary {
        Objects.requireNonNull(games, "Games must not be null");
    }
}