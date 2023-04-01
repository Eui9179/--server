package leui.woojoo.domain.today_games.service;

import leui.woojoo.domain.today_games.TodayGames;
import leui.woojoo.domain.today_games.dto.CreateTodayGameRequest;
import leui.woojoo.domain.today_games.dto.TodayGameDetail;
import leui.woojoo.domain.today_games.repository.TodayGamesRepository;
import leui.woojoo.domain.users.Users;
import leui.woojoo.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayGamesService {

    private final TodayGamesRepository todayGamesRepository;
    private final UsersService usersService;

    public List<TodayGameDetail> findAllByToday(Long userId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        List<TodayGames> todayGamesList = todayGamesRepository.findAllByCreatedDateBetweenOrderByIdDesc(today, tomorrow);
        return todayGamesList
                .stream()
                .map(todayGame -> TodayGameDetail.of(todayGame, userId))
                .toList();
    }

    public void save(Long userId, CreateTodayGameRequest todayGame) {
        Users user = usersService.findById(userId);
        TodayGames entity = TodayGames.builder()
                .users(user)
                .gameName(todayGame.getGame())
                .descriptions(todayGame.getIntroduction())
                .build();
        todayGamesRepository.save(entity);

        List<String> myFriendFcmTokenList = usersService.getMyFriendFcmTokenList(user);


    }
}
