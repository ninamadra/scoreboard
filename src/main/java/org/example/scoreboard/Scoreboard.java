package org.example.scoreboard;

import org.example.scoreboard.exception.GameNotFoundException;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.GamesSummary;
import org.example.scoreboard.model.dto.request.ScoreUpdate;

import java.util.UUID;

/**
 * A scoreboard library that keeps track of ongoing games and their scores.
 */
public interface Scoreboard {

    /**
     * Starts a new game with the given home and away teams.
     *
     * @param homeTeamName the name of the home team
     * @param awayTeamName the name of the away team
     * @return the newly started game
     */
    GameDto startGame(final String homeTeamName, final String awayTeamName);

    /**
     * Updates the score of an ongoing game.
     *
     * @param gameId the ID of the game to update
     * @param scoreUpdate the new scores for the home and away teams
     * @return the updated game
     * @throws GameNotFoundException if the game with the given ID does not exist
     * @throws IllegalArgumentException if any score in the score update is negative or not given
     */
    GameDto updateGame(final UUID gameId, final ScoreUpdate scoreUpdate) throws GameNotFoundException, IllegalArgumentException;

    /**
     * Finishes the game with the given ID, removing it from the scoreboard.
     *
     * @param gameId the ID of the game to finish
     * @throws GameNotFoundException if the game with the given ID does not exist
     */
    void finishGame(final UUID gameId) throws GameNotFoundException;

    /**
     * Gets a summary of all ongoing games, ordered by total score and start time.
     *
     * @return the summary of ongoing games
     */
    GamesSummary getOngoingGames();
}
