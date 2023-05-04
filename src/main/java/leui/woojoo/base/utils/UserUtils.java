package leui.woojoo.base.utils;

import org.springframework.security.core.userdetails.User;

import java.security.Principal;

public class UserUtils {
    public static Long resolveUserId(User user) {
        return Long.parseLong(user.getUsername());
    }

    public static Long resolveUserId(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
