package leui.woojoo.base.utils;

import leui.woojoo.bounded_context.games.entity.Games;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameUtils {
    public static List<String> getGameIntersection(List<Games> games1, List<Games> games2) {
        Set<String> game1Set = new HashSet<>(games1.stream().map(Games::getGame).toList());
        Set<String> game2Set = new HashSet<>(games2.stream().map(Games::getGame).toList());
        game1Set.retainAll(game2Set);

        return new ArrayList<>(game1Set);
    }
}
