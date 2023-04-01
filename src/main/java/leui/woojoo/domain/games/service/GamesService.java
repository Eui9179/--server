package leui.woojoo.domain.games.service;

import leui.woojoo.DataNotFoundException;
import leui.woojoo.domain.games.Games;
import leui.woojoo.domain.games.dto.Game;
import leui.woojoo.domain.games.repository.GamesQueryRepository;
import leui.woojoo.domain.games.repository.GamesRepository;
import leui.woojoo.domain.users.Users;
import leui.woojoo.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GamesService {
    private final GamesRepository gamesRepository;
    private final GamesQueryRepository gamesQueryRepository;
    private final UsersService usersService;

    public List<Game> findGamesByUserId(Long userId) {
        Users user = usersService.findById(userId);
        return user.getGames()
                .stream()
                .map(Game::new)
                .toList();
    }

    @Transactional
    public List<Game> updateGameList(Long userId, List<String> gameList) {
        Users user = usersService.findById(userId);
        List<Games> myGames = user.getGames();
        List<String> myGameNames = myGames.stream().map(Games::getGame).toList();

        List<Games> addedGameList = new ArrayList<>();
        List<Games> deletedGameList = new ArrayList<>();

        for (Games myGame : myGames) {
            if (!gameList.contains(myGame.getGame())) {
                deletedGameList.add(myGame);
            }
        }

        for (String game : gameList) {
            if (!myGameNames.contains(game)) {
                Games games = Games.builder()
                        .game(game)
                        .nickname(null)
                        .build();

                addedGameList.add(games);
            }
        }

        addAll(user, addedGameList);
        deleteAll(user, deletedGameList);

        return user.getGames()
                .stream()
                .map(Game::new)
                .toList();
    }

//    @Transactional
//    public void updateGameNickname(Long userId, String game, String nickname) {
//        Games gameEntity = gamesQueryRepository.findByUserIdAndGame(userId, game)
//                .orElseThrow(() -> new DataNotFoundException("게임 정보가 없습니다."));
//        gameEntity.updateGameNickname(nickname);
//    }

    @Transactional
    public void updateGameNickname(Long userId, String game, String nickname) {
        Users user = usersService.findById(userId);
        Games gameEntity = user.getGames()
                .stream()
                .filter(games -> games.getGame().equals(game))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("게임 이름이 등록되어 있지 않습니다."));
        gameEntity.updateGameNickname(nickname);
    }

    public void create(Users user, String game, String nickname) {
        Games gameEntity = Games.builder()
                .game(game)
                .nickname(nickname)
                .build();
        user.addGames(gameEntity);
        gamesRepository.save(gameEntity);
    }

    public void addAll(Users user, List<Games> gameList) {
        user.addGameList(gameList);
        gamesRepository.saveAll(gameList);
    }

    public void deleteAll(Users user, List<Games> gameList) {
        user.deleteGameList(gameList);
        gamesRepository.deleteAll(gameList);
    }

    public void delete(Users user, Games gameEntity) {
        user.deleteGames(gameEntity);
    }


}
