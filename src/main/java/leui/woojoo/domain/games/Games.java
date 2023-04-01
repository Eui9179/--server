package leui.woojoo.domain.games;

import jakarta.persistence.*;
import leui.woojoo.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class Games implements Comparable<Games>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users users;

    @Column(length = 50, nullable = false)
    private String game;

    @Column(length = 30)
    private String nickname;

    @Builder
    public Games(String game, String nickname) {
        this.game = game;
        this.nickname = nickname;
    }

    public void updateGameNickname(String gameNickname) {
        this.nickname = gameNickname;
    }

    @Override
    public int compareTo(Games o) {
        return this.game.compareTo(o.getGame());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Games compGame) {
            return this.game.equals(compGame.getGame());
        }
        return false;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
