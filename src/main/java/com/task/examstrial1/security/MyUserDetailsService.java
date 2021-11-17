package com.task.examstrial1.security;

import com.task.examstrial1.adapter.repository.UserRepository;
import com.task.examstrial1.exception.UserNotFoundException;
import com.task.examstrial1.model.SecurityUser;
import static org.springframework.security.core.userdetails.User.withUsername;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private  final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public MyUserDetailsService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecurityUser securityUser = userRepository.getUserByUsername(s)
                .orElseThrow(() -> new UserNotFoundException("User " + s + " is not found!"));
        System.out.println("securityUser = " + securityUser);
        UserDetails build = withUsername(securityUser.getUsername())
                .password(securityUser.getPassword())
                .authorities(securityUser
                        .getSecurityRoles().stream()
                        .map(securityRole -> new SimpleGrantedAuthority(securityRole.getRoleName()))
                        .collect(Collectors.toList()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
        System.out.println("build = " + build);
        return
                build;
    }



    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(
                    withUsername(jwtProvider.getUsername(jwtToken))
                            .authorities(jwtProvider.getRoles(jwtToken))
                            .password("") //token does not have password but field may not be empty
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }


//     * Extract the username from the JWT then lookup the user in the database.
    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(loadUserByUsername(jwtProvider.getUsername(jwtToken)));
        } else {
            return Optional.empty();
        }
    }

}
