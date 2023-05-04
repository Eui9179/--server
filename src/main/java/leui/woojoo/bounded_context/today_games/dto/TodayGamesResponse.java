package leui.woojoo.bounded_context.today_games.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodayGamesResponse {
    private List<TodayGameDetail> result;
}
