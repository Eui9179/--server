package leui.woojoo.bounded_context.today_games.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodayGameRequest {
    private String game;
    private String description;
}
