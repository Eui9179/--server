package leui.woojoo.bounded_context.games.entity;

import jakarta.persistence.*;
import leui.woojoo.bounded_context.users.entity.Users;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
