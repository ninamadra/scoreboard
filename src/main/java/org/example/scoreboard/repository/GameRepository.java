package org.example.scoreboard.repository;

import org.example.scoreboard.model.domain.Game;

public interface GameRepository {
    void save(final Game game);
}
