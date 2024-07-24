package org.example.scoreboard.model.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
