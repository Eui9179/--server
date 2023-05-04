package leui.woojoo.bounded_context.games.dto;

import leui.woojoo.bounded_context.games.entity.Games;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {
    private String game;
    private String nickname;

    public Game(Games games) {
        this.game  = games.getGame();
        this.nickname = games.getNickname();
    }
}
