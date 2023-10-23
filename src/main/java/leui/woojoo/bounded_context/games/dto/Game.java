package leui.woojoo.bounded_context.games.dto;

import leui.woojoo.bounded_context.games.entity.Games;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {
    private String name;
    private String nickname;

    public Game(Games games) {
        this.name = games.getGame();
        this.nickname = games.getNickname();
    }
}
