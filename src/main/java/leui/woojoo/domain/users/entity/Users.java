package leui.woojoo.domain.users.entity;

import jakarta.persistence.*;
import leui.woojoo.domain.BaseTimeEntity;
import leui.woojoo.domain.games.entity.Games;
import leui.woojoo.domain.groups.entity.Groups;
import leui.woojoo.domain.users.dto.UserSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 200)
    private String profileImageName;

    @Column(length = 200)
    private String fcmToken;

    @ManyToMany
    Set<Users> friends;

    @ManyToMany
    Set<Users> blocklist;

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Games> games = new ArrayList<>();

    @OneToOne(mappedBy = "users", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Groups groups;

    public void asyncFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public UserSimple toProfile() {
        return UserSimple.builder()
                .userId(id)
                .name(name)
                .profileImageName(profileImageName)
                .build();
    }

    public void updateProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public void updateUserName(String name) {
        this.name = name;
    }

    public void addGames(Games game) {
        games.add(game);
        game.setUsers(this);
    }

    public void deleteGames(Games game) {
        games.remove(game);
        game.setUsers(null);
    }

    public void addGameList(List<Games> games) {
        this.games.addAll(games);
        for (Games game : games) {
            game.setUsers(this);
        }
    }

    public void deleteGameList(List<Games> gameList) {
        games.removeAll(gameList);
    }

    public void addFriend(Users friend) {
        this.friends.add(friend);
    }
}
