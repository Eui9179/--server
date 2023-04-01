package leui.woojoo.domain.today_games.service;

import leui.woojoo.domain.today_games.TodayGames;
import leui.woojoo.domain.today_games.dto.TodayGameDetail;
import leui.woojoo.domain.today_games.repository.TodayGamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayGamesService {

    private final TodayGamesRepository todayGamesRepository;

    public List<TodayGameDetail> findAllByToday(Long userId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        List<TodayGames> todayGamesList = todayGamesRepository.findAllByCreatedDateBetweenOrderByIdDesc(today, tomorrow);
        return todayGamesList
                .stream()
                .map(todayGame -> TodayGameDetail.of(todayGame, userId))
                .toList();
    }
}
