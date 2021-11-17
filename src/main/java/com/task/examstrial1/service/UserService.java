package com.task.examstrial1.service;

import com.task.examstrial1.adapter.repository.UserRepository;
import com.task.examstrial1.exception.NoSuchUser;
import com.task.examstrial1.model.SecurityUser;
import com.task.examstrial1.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    public UserService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;

        this.userRepository = userRepository;

    }

    public Optional<SecurityUser> save(SecurityUser securityUser){
        Authentication authentication= new UsernamePasswordAuthenticationToken(securityUser,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Optional.ofNullable(userRepository.save(securityUser));
    }


    public Optional<String> signIn(String username, String password){
        Optional<String > token = Optional.empty();
        Optional<SecurityUser> theUser = userRepository.getUserByUsername(username);
        if (theUser.isPresent()){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
               token= Optional.of(jwtProvider.createToken(username,theUser.get().getSecurityRoles()));
            }
            catch (BadCredentialsException e){
                throw new NoSuchUser("Wrong username or password!",username);
            }
        }
        return token;

    }





    public Optional<SecurityUser> findUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public Optional<SecurityUser> findUserById(Long id){
        return userRepository.getUserById(id);
    }
    public List<SecurityUser> findAll(){
        return userRepository.getAllUsers();
    }




}
