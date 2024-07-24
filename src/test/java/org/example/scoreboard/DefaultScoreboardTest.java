package org.example.scoreboard;


import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.repository.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultScoreboardTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private DefaultScoreboard underTest;


    @Test
    @DisplayName("Should start a game")
    void shouldStartGame() {
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
    @DisplayName("Should throw IllegalArgumentException when team name is empty string")
    void shouldThrowIllegalArgumentExceptionWhenHomeTeamNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> underTest.startGame("", "Away"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when team name is null")
    void shouldThrowIllegalArgumentExceptionWhenHomeTeamNameIsNull() {
       assertThrows(IllegalArgumentException.class, () -> underTest.startGame("Home", null));
    }

}