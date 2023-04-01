package leui.woojoo.domain.games.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UpdatedGameNicknameRequest {
    private String game;
    private String nickname;
}
