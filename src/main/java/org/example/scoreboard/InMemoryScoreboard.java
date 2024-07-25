package org.example.scoreboard;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.GamesSummary;
import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.example.scoreboard.repository.TreeMapGameRepository;

import java.util.UUID;

/**
 * Facade for the {@link DefaultScoreboard} class, using a {@link TreeMapGameRepository} as the repository.
 */
public class InMemoryScoreboard implements Scoreboard {

    private final DefaultScoreboard defaultScoreboard = new DefaultScoreboard(new TreeMapGameRepository());

    @Override
    public GameDto startGame(final String homeTeamName, final String awayTeamName) throws IllegalArgumentException {
        return defaultScoreboard.startGame(homeTeamName, awayTeamName);
    }


    @Override
    public GameDto updateGame(final UUID gameId, final ScoreUpdate scoreUpdate) throws GameNotFoundException, IllegalArgumentException {
        return defaultScoreboard.updateGame(gameId, scoreUpdate);
    }


    @Override
    public void finishGame(final UUID gameId) throws GameNotFoundException {
        defaultScoreboard.finishGame(gameId);
    }


    @Override
    public GamesSummary getOngoingGames() {
        return defaultScoreboard.getOngoingGames();
    }
}
