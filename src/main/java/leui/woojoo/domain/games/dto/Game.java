package leui.woojoo.domain.games.dto;

import leui.woojoo.domain.games.entity.Games;
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
