package org.example.scoreboard.repository;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.domain.Game;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Stream;

public class InMemoryGameRepository implements GameRepository {

    private final Map<UUID, Game> gameLookup = new HashMap<>();
    private final SortedSet<Game> gameSortedSet = new TreeSet<>(Comparator
            .comparing(Game::getTotalScore, Comparator.reverseOrder())
            .thenComparing(Game::getCreatedAt, Comparator.reverseOrder()));

    @Override
    public void save(final Game game) {
        Objects.requireNonNull(game, "Game must not be null");
        if (existsById(game.getId())) {
            final Game toUpdate = gameLookup.get(game.getId());
            gameSortedSet.remove(toUpdate);

        } else {
            gameLookup.put(game.getId(), game);
        }
        gameSortedSet.add(game);
    }


    @Override
    public boolean existsById(final UUID gameId) {
        return gameLookup.containsKey(gameId);
    }


    @Override
    public void deleteById(final UUID gameId) {
        final Game game = gameLookup.get(gameId);
        gameSortedSet.remove(game);
        gameLookup.remove(gameId);
    }


    @Override
    public Game findByIdOrThrow(final UUID gameId) throws GameNotFoundException {
        final Game game = gameLookup.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Game with ID " + gameId + " not found");
        }
        return game;
    }


    @Override
    public Stream<Game> getAllOrderedByTotalScoreAndStartTime() {
        return gameSortedSet.stream();
    }
}