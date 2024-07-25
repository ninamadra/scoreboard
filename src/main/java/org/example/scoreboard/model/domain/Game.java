package org.example.scoreboard.model.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scoreboard.model.dto.request.ScoreUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    private final UUID id;

    private final Team homeTeam;

    private final Team awayTeam;

    private final OffsetDateTime createdAt;


    public static Game from(final String homeTeamName, final String awayTeamName) {
        return new Game(
                UUID.randomUUID(),
                Team.from(homeTeamName),
                Team.from(awayTeamName),
                OffsetDateTime.now()
        );
    }


    public void updateScore(final ScoreUpdate scoreUpdate) {
        if (scoreUpdate == null || scoreUpdate.homeTeamScore() == null || scoreUpdate.awayTeamScore() == null) {
            throw new IllegalArgumentException("Score update and scores must not be null");
        }
        homeTeam.setScore(scoreUpdate.homeTeamScore());
        awayTeam.setScore(scoreUpdate.awayTeamScore());
    }
}
