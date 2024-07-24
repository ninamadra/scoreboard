package org.example.scoreboard;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.mapper.GameMapper;
import org.example.scoreboard.model.domain.Game;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.GamesSummary;
import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.example.scoreboard.repository.GameRepository;

import java.util.UUID;

public class DefaultScoreboard implements Scoreboard {

    private final GameRepository gameRepository;


    public DefaultScoreboard(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @Override
    public GameDto startGame(final String homeTeamName, final String awayTeamName) {
        final Game game = Game.from(homeTeamName, awayTeamName);
        gameRepository.save(game);
        return GameMapper.toDto(game);
    }


    @Override
    public GameDto updateGame(final UUID gameId, final ScoreUpdate scoreUpdate) throws GameNotFoundException, IllegalArgumentException {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public void finishGame(final UUID gameId) throws GameNotFoundException {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Override
    public GamesSummary getOngoingGames() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
