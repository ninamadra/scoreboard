package org.example.scoreboard;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.mapper.GameMapper;
import org.example.scoreboard.model.domain.Game;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.GamesSummary;
import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.example.scoreboard.repository.GameRepository;

import java.util.List;
import java.util.UUID;

/**
 * Default implementation of the {@link Scoreboard} interface.
 */
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
        final Game game = gameRepository.findByIdOrThrow(gameId);
        game.updateScore(scoreUpdate);
        gameRepository.save(game);
        return GameMapper.toDto(game);
    }


    @Override
    public void finishGame(final UUID gameId) throws GameNotFoundException {
        final boolean exists = gameRepository.existsById(gameId);
        if (!exists) {
            throw new GameNotFoundException("Game with ID = {} not found", gameId);
        }
        gameRepository.deleteById(gameId);
    }


    @Override
    public GamesSummary getOngoingGames() {
        final List<GameDto> games = gameRepository.getAllOrderedByTotalScoreAndStartTime()
                .sequential()
                .map(GameMapper::toDto)
                .toList();
        return new GamesSummary(games);
    }
}
