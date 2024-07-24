package org.example.scoreboard.repository;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.domain.Game;

import java.util.UUID;

/**
 * Repository for {@link Game} entities.
 */
public interface GameRepository {

    void save(final Game game);

    boolean existsById(final UUID gameId);

    void deleteById(final UUID gameId);

    Game findByIdOrThrow(final UUID gameId) throws GameNotFoundException;
}
