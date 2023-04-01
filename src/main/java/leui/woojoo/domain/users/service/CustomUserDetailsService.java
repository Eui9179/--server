package leui.woojoo.domain.users.service;

import leui.woojoo.domain.users.entity.Users;
import leui.woojoo.domain.users.entity.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
//        return usersRepository.findById(Long.parseLong(username))
//                .map(this::createUser)
//                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        return createUser(username);
    }

    private User createUser(Users user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return new User(user.getId().toString(), "", grantedAuthorities);
    }

    private User createUser(String userId) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return new User(userId, "", grantedAuthorities);
    }
}
