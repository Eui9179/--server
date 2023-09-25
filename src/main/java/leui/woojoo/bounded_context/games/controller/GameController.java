package leui.woojoo.bounded_context.games.controller;

import leui.woojoo.bounded_context.games.dto.Game;
import leui.woojoo.bounded_context.games.dto.UpdatedGameNicknameRequest;
import leui.woojoo.bounded_context.games.service.GamesService;
import leui.woojoo.base.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GamesService gamesService;

    @GetMapping("/me")
    public List<Game> getMyGames(@AuthenticationPrincipal User user) {
        Long userId = UserUtils.resolveUserId(user);
        return gamesService.findGamesByUserId(userId);
    }

    @PostMapping
    public List<Game> updateMyGames(@AuthenticationPrincipal User user, @RequestBody List<String> gameList) {
        Long userId = UserUtils.resolveUserId(user);
        return gamesService.updateGameList(userId, gameList);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/nickname")
    public String updateGameNickname(Principal principal, @RequestBody UpdatedGameNicknameRequest nicknameRequest) {
        Long userId = UserUtils.resolveUserId(principal);
        gamesService.updateGameNickname(userId, nicknameRequest.getGame(), nicknameRequest.getNickname());
        return "ok";
    }
}
