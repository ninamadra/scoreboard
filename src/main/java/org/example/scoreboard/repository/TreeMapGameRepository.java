package org.example.scoreboard.repository;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.domain.Game;

import java.time.OffsetDateTime;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Stream;

public class TreeMapGameRepository implements GameRepository {

    private final TreeMap<ScoreAndTimeKey, Game> games = new TreeMap<>();

    @Override
    public void save(final Game game) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public boolean existsById(final UUID gameId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public void deleteById(final UUID gameId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public Game findByIdOrThrow(final UUID gameId) throws GameNotFoundException {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public Stream<Game> getAllOrderedByTotalScoreAndStartTime() {
        return games.values().stream();
    }


        private record ScoreAndTimeKey(int totalScore, OffsetDateTime startTime,
                                       UUID id) implements Comparable<ScoreAndTimeKey> {
            @Override
            public int compareTo(final ScoreAndTimeKey other) {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        }
}
