package leui.woojoo.bounded_context.today_games.entity;

import jakarta.persistence.*;
import leui.woojoo.bounded_context.BaseTimeEntity;
import leui.woojoo.bounded_context.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TodayGames extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users users;

    @Column(length = 40, nullable = false)
    private String gameName;

    @Column(length = 200)
    private String descriptions;

    @Builder
    public TodayGames(Users users, String gameName, String descriptions) {
        this.users = users;
        this.gameName = gameName;
        this.descriptions = descriptions;
    }
}
