package org.example.scoreboard.model.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scoreboard.model.valueobject.Score;
import org.example.scoreboard.util.ValidationUtils;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Team {

    private final String name;

    private Score score = new Score(0);

    public static Team from(final String name) {
        ValidationUtils.requireNonBlank(name);
        return new Team(name);
    }
}
