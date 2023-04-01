package leui.woojoo.utils;

import java.util.Map;

public class ToKor {
    private static final Map<String, String> korMapping = Map.ofEntries(
            Map.entry("leagueoflegends", "리그 오브 레전드"),
            Map.entry("overwatch", "오버워치"),
            Map.entry("valorant", "발로란트"),
            Map.entry("tft", "전략적 팀 전투"),
            Map.entry("battleground", "배틀그라운드"),
            Map.entry("lostark", "로스트아크"),
            Map.entry("minecraft", "마인크래프트"),
            Map.entry("fifaonline4", "피파 온라인4"),
            Map.entry("starcraft", "스타크래프트"),
            Map.entry("starcraft2", "스타크래프트2"),
            Map.entry("counterstrike", "카운터스트라이크"),
            Map.entry("apexlegends", "에픽 레전드"),
            Map.entry("fortnite", "포트나이트"),
            Map.entry("gta5", "gta 5"),
            Map.entry("dota2", "도타 2"),
            Map.entry("fallguys", "폴가이즈"),
            Map.entry("callofduty", "콜오브듀티"),
            Map.entry("worldofwarcraft", "월드오브워크래프트"),
            Map.entry("hearthstone", "하스스톤"),
            Map.entry("maplestory", "메이플스토리"),
            Map.entry("suddenattack", "서든어택"),
            Map.entry("dungeonandfighter", "던전앤파이터"),
            Map.entry("diablo2", "디아블로2"),
            Map.entry("roblox", "로블록스")
    );
    public static String gameNameToKor(String game) {
        return korMapping.get(game);
    }
}
