package leui.woojoo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenUtils implements Runnable {
    private List<String> fcmTokenList;
    private String userName;
    private String game;
    private String gameDescription;

    @Override
    public void run() {
        send();
    }

    public void send() {
        for (String fcmToken : fcmTokenList) {
            String body = this.gameDescription == null? ToKor.gameNameToKor(this.game):
                    ToKor.gameNameToKor(this.game) + " - " + this.gameDescription;
            Map<String, Object> data = Map.of(
                    "to", fcmToken,
                    "priority", "high",
                    "notification", Map.of(
                            "title", this.userName + "님의 오늘의 게임",
                            "body", body
                    )
            );
        }

    }

    public static FcmTokenUtils of(List<String> fcmTokenList, String userName, String game, String gameDescription) {
        return new FcmTokenUtils(fcmTokenList, userName, game, gameDescription);
    }
}
