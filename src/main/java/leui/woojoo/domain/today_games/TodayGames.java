package leui.woojoo.domain.today_games;

import jakarta.persistence.*;
import leui.woojoo.domain.BaseTimeEntity;
import leui.woojoo.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
