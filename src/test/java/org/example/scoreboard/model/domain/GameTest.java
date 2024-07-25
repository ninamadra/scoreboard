package org.example.scoreboard.model.domain;

import org.example.scoreboard.model.dto.request.ScoreUpdate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GameTest {

    @Test
    @DisplayName("Test updateScore should update score")
    void testUpdateScoreShouldUpdateScore() {
        final Game game = Game.from("Home", "Away");
        final ScoreUpdate scoreUpdate = new ScoreUpdate(1, 2);

        game.updateScore(scoreUpdate);

        assertEquals(1, game.getHomeTeam().getScore().value());
        assertEquals(2, game.getAwayTeam().getScore().value());
    }

    @Test
    @DisplayName("Test updateScore should throw IllegalArgumentException when score update is null")
    void testUpdateScoreShouldThrowIllegalArgumentExceptionWhenScoreUpdateIsNull() {
        final Game game = Game.from("Home", "Away");
        assertThrows(IllegalArgumentException.class, () -> game.updateScore(null));
    }

}