package leui.woojoo.bounded_context.today_games.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import leui.woojoo.bounded_context.today_games.dto.CreateTodayGameRequest;
import leui.woojoo.bounded_context.today_games.dto.TodayGameDetail;
import leui.woojoo.bounded_context.today_games.service.TodayGamesService;
import leui.woojoo.base.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/games/todays")
public class TodayGamesController {

    private final TodayGamesService todayGamesService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<TodayGameDetail> getTodayGames(Principal principal) {
        Long userId = UserUtils.resolveUserId(principal);
        return todayGamesService.findAllByToday(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String createTodayGames(Principal principal, @RequestBody CreateTodayGameRequest request) throws FirebaseMessagingException {
        Long userId = UserUtils.resolveUserId(principal);
        todayGamesService.save(userId, request);
        return "ok";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{todayGameId}")
    public String deleteTodayGame(@PathVariable Long todayGameId) {
        todayGamesService.deleteById(todayGameId);
        return "ok";
    }
}