package org.example.scoreboard;


import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.domain.Game;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.GamesSummary;
import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.example.scoreboard.repository.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultScoreboardTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private DefaultScoreboard underTest;


    @Test
    @DisplayName("Test startGame should start a game")
    void testStartGameShouldStartGame() {
        final String homeTeamName = "Home";
        final String awayTeamName = "Away";

        doNothing().when(gameRepository).save(any());

        final GameDto result = underTest.startGame(homeTeamName, awayTeamName);
        assertEquals(homeTeamName, result.homeTeam().name());
        assertEquals(awayTeamName, result.awayTeam().name());
        assertEquals(0, result.homeTeam().score().value());
        assertEquals(0, result.awayTeam().score().value());

        verify(gameRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Test startGame should throw IllegalArgumentException when team name is empty string")
    void testStartGameShouldThrowIllegalArgumentExceptionWhenHomeTeamNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> underTest.startGame("", "Away"));
    }

    @Test
    @DisplayName("Test startGame should throw IllegalArgumentException when team name is null")
    void testStartGameShouldThrowIllegalArgumentExceptionWhenHomeTeamNameIsNull() {
       assertThrows(IllegalArgumentException.class, () -> underTest.startGame("Home", null));
    }

    @Test
    @DisplayName("Test finishGame should delete a game by ID")
    void testFinishGameShouldDeleteGameById() {
        final var gameId = UUID.randomUUID();
        when(gameRepository.existsById(gameId)).thenReturn(true);
        doNothing().when(gameRepository).deleteById(gameId);

        underTest.finishGame(gameId);

        verify(gameRepository, times(1)).existsById(gameId);
        verify(gameRepository, times(1)).deleteById(gameId);
    }

    @Test
    @DisplayName("Test finishGame should throw GameNotFoundException when game does not exist")
    void testFinishGameShouldThrowGameNotFoundExceptionWhenGameDoesNotExist() {
        final var gameId = UUID.randomUUID();
        when(gameRepository.existsById(gameId)).thenReturn(false);

        assertThrows(GameNotFoundException.class, () -> underTest.finishGame(gameId));

        verify(gameRepository, times(1)).existsById(gameId);
        verify(gameRepository, times(0)).deleteById(gameId);
    }

    @Test
    @DisplayName("Test updateGame should update a game")
    void testUpdateGameShouldUpdateGame() {
        final var gameId = UUID.randomUUID();
        final var scoreUpdate = new ScoreUpdate(1, 2);
        final var game = Game.from("Home", "Away");
        when(gameRepository.findByIdOrThrow(gameId)).thenReturn(game);

        final var result = underTest.updateGame(gameId, scoreUpdate);

        assertEquals(1, result.homeTeam().score().value());
        assertEquals(2, result.awayTeam().score().value());

        verify(gameRepository, times(1)).findByIdOrThrow(gameId);
    }

    @Test
    @DisplayName("Test updateGame should throw GameNotFoundException when game does not exist")
    void testUpdateGameShouldThrowGameNotFoundExceptionWhenGameDoesNotExist() {
        final var gameId = UUID.randomUUID();
        final var scoreUpdate = new ScoreUpdate(1, 2);
        when(gameRepository.findByIdOrThrow(gameId)).thenThrow(new GameNotFoundException("Game with ID = {} not found", gameId));

        assertThrows(GameNotFoundException.class, () -> underTest.updateGame(gameId, scoreUpdate));

        verify(gameRepository, times(1)).findByIdOrThrow(gameId);
    }

    @Test
    @DisplayName("Test updateGame should throw IllegalArgumentException when score update is null")
    void testUpdateGameShouldThrowIllegalArgumentExceptionWhenScoreUpdateIsNull() {
        final var gameId = UUID.randomUUID();
        when(gameRepository.findByIdOrThrow(gameId)).thenReturn(Game.from("Home", "Away"));

        assertThrows(IllegalArgumentException.class, () -> underTest.updateGame(gameId, null));

        verify(gameRepository, times(1)).findByIdOrThrow(gameId);
    }

    @Test
    @DisplayName("Test getOngoingGames should return a summary of ongoing games")
    void testGetOngoingGamesShouldReturnSummaryOfOngoingGames() {
        final String homeTeamName = "Home1";
        final String awayTeamName = "Away1";
        final String homeTeamName2 = "Home2";
        final String awayTeamName2 = "Away2";

        final Game game = Game.from(homeTeamName, awayTeamName);
        final Game game2 = Game.from(homeTeamName2, awayTeamName2);
        when(gameRepository.getAllOrderedByTotalScoreAndStartTime())
                .thenReturn(Stream.of(game, game2));

        final GamesSummary result = underTest.getOngoingGames();
        final List<GameDto> games = result.games();

        assertEquals(2, games.size());
        assertEquals(homeTeamName, games.getFirst().homeTeam().name());
        assertEquals(awayTeamName, games.getFirst().awayTeam().name());
        assertEquals(homeTeamName2, games.getLast().homeTeam().name());
        assertEquals(awayTeamName2, games.getLast().awayTeam().name());

        verify(gameRepository, times(1)).getAllOrderedByTotalScoreAndStartTime();
    }

    @Test
    @DisplayName("Test getOngoingGames should return an empty list if there are no ongoing games")
    void testGetOngoingGamesShouldReturnEmptyListIfNoOngoingGames() {
        when(gameRepository.getAllOrderedByTotalScoreAndStartTime())
                .thenReturn(Stream.empty());

        final GamesSummary result = underTest.getOngoingGames();
        final List<GameDto> games = result.games();

        assertEquals(0, games.size());

        verify(gameRepository, times(1)).getAllOrderedByTotalScoreAndStartTime();
    }

    @Test
    @DisplayName("Test getOngoingGames should use a sequential stream")
    void testGetOngoingGamesShouldUseSequentialStream() {
        final String homeTeamName = "Home1";
        final String awayTeamName = "Away1";
        final String homeTeamName2 = "Home2";
        final String awayTeamName2 = "Away2";

        final Game game = Game.from(homeTeamName, awayTeamName);
        final Game game2 = Game.from(homeTeamName2, awayTeamName2);
        Stream<Game> gameStream = Stream.of(game, game2).parallel();
        when(gameRepository.getAllOrderedByTotalScoreAndStartTime()).thenReturn(gameStream);

        final var result = underTest.getOngoingGames();
        final List<GameDto> games = result.games();

        assertFalse(gameStream.isParallel(), "Stream should be sequential");
        assertEquals(2, result.games().size());
        assertEquals(homeTeamName, games.getFirst().homeTeam().name());
        assertEquals(awayTeamName, games.getFirst().awayTeam().name());
        assertEquals(homeTeamName2, games.getLast().homeTeam().name());
        assertEquals(awayTeamName2, games.getLast().awayTeam().name());

        verify(gameRepository, times(1)).getAllOrderedByTotalScoreAndStartTime();
    }

}