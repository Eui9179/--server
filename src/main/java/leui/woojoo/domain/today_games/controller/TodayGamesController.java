package leui.woojoo.domain.today_games.controller;

import leui.woojoo.domain.today_games.TodayGames;
import leui.woojoo.domain.today_games.dto.TodayGameDetail;
import leui.woojoo.domain.today_games.dto.TodayGamesResponse;
import leui.woojoo.domain.today_games.service.TodayGamesService;
import leui.woojoo.domain.users.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/games/todays")
public class TodayGamesController {

    private final TodayGamesService todayGamesService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public TodayGamesResponse getTodayGames(Principal principal) {
        Long userId = UserUtils.resolveUserId(principal);
        return new TodayGamesResponse(todayGamesService.findAllByToday(userId));
    }
}