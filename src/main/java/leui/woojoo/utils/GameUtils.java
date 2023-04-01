package leui.woojoo.utils;

import leui.woojoo.domain.games.entity.Games;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<String> getGameIntersection(List<Games> games1, List<Games> games2) {
        List<String> myGameNames = new ArrayList<>(games1.stream()
                .map(Games::getGame)
                .toList());

        List<String> otherGameNames = new ArrayList<>(games2.stream()
                .map(Games::getGame)
                .toList());

        List<String> result = new ArrayList<>();

        for (String otherGameName : otherGameNames) {
            if (myGameNames.contains(otherGameName)) result.add(otherGameName);
        }
        return result;
    }
}
