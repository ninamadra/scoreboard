package org.example.scoreboard.repository;


import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.domain.Game;
import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class InMemoryGameRepositoryTest {

    private InMemoryGameRepository inMemoryGameRepository;


    @BeforeEach
    void setUp() {
        inMemoryGameRepository = new InMemoryGameRepository();
    }

    @Test
    @DisplayName("Test existsById returns false when no games are stored")
    void testExistsByIdReturnsFalseWhenNoGamesAreStored() {
        final boolean exists = inMemoryGameRepository.existsById(Game.from("Home", "Away").getId());
        assertFalse(exists);
    }

    @Test
    @DisplayName("Test existsById returns true when a game with the given ID exists")
    void testExistsByIdReturnsTrueWhenAGameWithTheGivenIdExists() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        final boolean exists = inMemoryGameRepository.existsById(game.getId());
        assertTrue(exists);
    }

    @Test
    @DisplayName("Test existsById returns false when no game with the given ID exists")
    void testExistsByIdReturnsFalseWhenNoGameWithTheGivenIdExists() {
        final boolean exists = inMemoryGameRepository.existsById(Game.from("Home", "Away").getId());
        assertFalse(exists);
    }

    @Test
    @DisplayName("Test save adds a game to the repository")
    void testSaveAddsAGameToTheRepository() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        final boolean exists = inMemoryGameRepository.existsById(game.getId());
        assertTrue(exists);
    }

    @Test
    @DisplayName("Test save updates an existing game in the repository")
    void testSaveUpdatesAnExistingGameInTheRepository() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        game.updateScore(new ScoreUpdate(1, 1));
        inMemoryGameRepository.save(game);

        final Game foundGame = inMemoryGameRepository.findByIdOrThrow(game.getId());
        assertEquals(2, foundGame.getTotalScore());
        assertEquals(1, inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().count());
    }

    @Test
    @DisplayName("Test deleteById removes a game from the repository")
    void testDeleteByIdRemovesAGameFromTheRepository() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        inMemoryGameRepository.deleteById(game.getId());
        final boolean exists = inMemoryGameRepository.existsById(game.getId());
        assertFalse(exists);
    }


    @Test
    @DisplayName("Test deleteById does nothing when no game with the given ID exists")
    void testDeleteByIdDoesNothingWhenNoGameWithTheGivenIdExists() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        inMemoryGameRepository.deleteById(game.getId());
        inMemoryGameRepository.deleteById(game.getId());
        final boolean exists = inMemoryGameRepository.existsById(game.getId());
        assertFalse(exists);
    }


    @Test
    @DisplayName("Test deleteById does nothing when no games are stored")
    void testDeleteByIdDoesNothingWhenNoGamesAreStored() {
        inMemoryGameRepository.deleteById(Game.from("Home", "Away").getId());
        final boolean exists = inMemoryGameRepository.existsById(Game.from("Home", "Away").getId());
        assertFalse(exists);
    }


    @Test
    @DisplayName("Test deleteById deletes the correct game from the repository")
    void testDeleteByIdDeletesTheCorrectGameFromTheRepository() {
        final Game game1 = Game.from("Home", "Away");
        final Game game2 = Game.from("Home", "Away");

        inMemoryGameRepository.save(game1);
        inMemoryGameRepository.save(game2);
        inMemoryGameRepository.deleteById(game1.getId());

        final boolean exists1 = inMemoryGameRepository.existsById(game1.getId());
        final boolean exists2 = inMemoryGameRepository.existsById(game2.getId());

        assertFalse(exists1);
        assertTrue(exists2);
        assertEquals(1, inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().count());
    }


    @Test
    @DisplayName("Test findByIdOrThrow returns the correct game")
    void testFindByIdOrThrowReturnsTheCorrectGame() {
        final Game game = Game.from("Home", "Away");
        inMemoryGameRepository.save(game);
        final Game foundGame = inMemoryGameRepository.findByIdOrThrow(game.getId());
        assertEquals(game, foundGame);
    }

    @Test
    @DisplayName("Test findByIdOrThrow throws GameNotFoundException when no games are stored")
    void testFindByIdOrThrowThrowsGameNotFoundExceptionWhenNoGamesAreStored() {
        assertThrows(GameNotFoundException.class, () -> inMemoryGameRepository.findByIdOrThrow(Game.from("Home", "Away").getId()));
    }


    @Test
    @DisplayName("Test findByIdOrThrow throws GameNotFoundException when no game with the given ID exists")
    void testFindByIdOrThrowThrowsGameNotFoundExceptionWhenNoGameWithTheGivenIdExists() {
        final Game game = Game.from("Home", "Away");
        final Game game2 = Game.from("Home", "Away");
        final Game game3 = Game.from("Home", "Away");

        inMemoryGameRepository.save(game);
        inMemoryGameRepository.save(game2);
        inMemoryGameRepository.save(game3);

        assertThrows(GameNotFoundException.class, () -> inMemoryGameRepository.findByIdOrThrow(Game.from("Home", "Away").getId()));
    }

    @Test
    @DisplayName("Test getAllOrderedByTotalScoreAndStartTime returns games ordered by total score")
    void testGetAllOrderedByTotalScoreAndStartTimeReturnsGamesOrderedByTotalScore() {
        final Game game1 = Game.from("Home", "Away");
        final Game game2 = Game.from("Home", "Away");
        final Game game3 = Game.from("Home", "Away");

        game1.updateScore(new ScoreUpdate(1, 1));
        game2.updateScore(new ScoreUpdate(3, 2));
        game3.updateScore(new ScoreUpdate(0, 4));

        inMemoryGameRepository.save(game1);
        inMemoryGameRepository.save(game2);
        inMemoryGameRepository.save(game3);

        final var games = inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().toList();

        assertEquals(3, games.size());
        assertEquals(game2, games.get(0));
        assertEquals(game3, games.get(1));
        assertEquals(game1, games.get(2));
    }

    @Test
    @DisplayName("Test getAllOrderedByTotalScoreAndStartTime returns games ordered by start time")
    void testGetAllOrderedByTotalScoreAndStartTimeReturnsGamesOrderedByStartTime() {
        final Game game1 = Game.from("Home", "Away");
        final Game game2 = Game.from("Home", "Away");
        final Game game3 = Game.from("Home", "Away");

        inMemoryGameRepository.save(game1);
        inMemoryGameRepository.save(game2);
        inMemoryGameRepository.save(game3);

        final var games = inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().toList();

        assertEquals(3, games.size());
        assertEquals(game3, games.get(0));
        assertEquals(game2, games.get(1));
        assertEquals(game1, games.get(2));
    }

    @Test
    @DisplayName("Test getAllOrderedByTotalScoreAndStartTime returns games ordered by total score and start time")
    void testGetAllOrderedByTotalScoreAndStartTimeReturnsGamesOrderedByTotalScoreAndStartTime() {
        final Game game1 = Game.from("Home", "Away");
        final Game game2 = Game.from("Home", "Away");
        final Game game3 = Game.from("Home", "Away");

        game1.updateScore(new ScoreUpdate(1, 0));
        game2.updateScore(new ScoreUpdate(0, 1));
        game3.updateScore(new ScoreUpdate(3, 1));

        inMemoryGameRepository.save(game1);
        inMemoryGameRepository.save(game2);
        inMemoryGameRepository.save(game3);

        final var games = inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().toList();

        assertEquals(3, games.size());
        assertEquals(game3, games.get(0));
        assertEquals(game2, games.get(1));
        assertEquals(game1, games.get(2));
    }

    @Test
    @DisplayName("Test getAllOrderedByTotalScoreAndStartTime returns empty stream when no games are stored")
    void testGetAllOrderedByTotalScoreAndStartTimeReturnsEmptyStreamWhenNoGamesAreStored() {
        final List<Game> games = inMemoryGameRepository.getAllOrderedByTotalScoreAndStartTime().toList();
        assertTrue(games.isEmpty());
    }
}