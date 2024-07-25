package org.example.scoreboard.mapper;

import org.example.scoreboard.model.domain.Game;
import org.example.scoreboard.model.dto.GameDto;
import org.example.scoreboard.model.dto.TeamDto;

public class GameMapper {
    public static GameDto toDto(Game game) {
        return new GameDto(
                game.getId(),
                new TeamDto(game.getHomeTeam().getName(), game.getHomeTeam().getScore()),
                new TeamDto(game.getAwayTeam().getName(), game.getAwayTeam().getScore())
        );
    }
}
