package com.task.examstrial1.adapter.rest.controller;

import com.task.examstrial1.adapter.rest.dto.UserDTO;
import com.task.examstrial1.entity.RoleEntity;
import com.task.examstrial1.exception.NoSuchUser;
import com.task.examstrial1.exception.UserAlreadyExists;
import com.task.examstrial1.model.SecurityRole;
import com.task.examstrial1.model.SecurityUser;
import com.task.examstrial1.security.AuthenticationRequest;
import com.task.examstrial1.security.AuthenticationResponse;
import com.task.examstrial1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;

        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {

        Optional<String> token = userService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token
                .orElseThrow(()-> new BadCredentialsException("Wrong username or password! "))));
    }





    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/register")
    public UserDTO register( @RequestBody  UserDTO userDTO, @RequestParam String password){
        SecurityUser newSecurityUser = SecurityUser.builder()
                .username(userDTO.getUsername())
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(password))
                .securityRoles(Stream.of(SecurityRole.builder()
                        .roleName("user")
                        .description("user")
                        .build()).collect(Collectors.toList()))
                .build();
        return toUserDTO(userService.save(newSecurityUser)
                .orElseThrow(()-> new UserAlreadyExists("User Already exist",userDTO.getUsername())));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public List<UserDTO> gettingAllUsers(){
        return userService.findAll().stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }













    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/access/{userId}/{userRole}")
    public String giveAccessToUser(@PathVariable Long userId,@PathVariable String userRole,Principal principal){
        Optional<SecurityUser> userById = userService.findUserById(userId);
        List<String> activeRoles = getRolesOfLoggedInUser(principal);
        if (activeRoles.contains(userRole)){
            userById.get().getSecurityRoles()
                    .add(SecurityRole.builder()
                            .roleName(userRole)
                            .description(userRole)
                            .build());
            userService.save(userById.get());
        }
        return "you've been assigned to new role access " + userRole + " by "+ principal.getName();

    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        return "ok";

    }




    private UserDTO toUserDTO(SecurityUser user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .build();
    }


    private SecurityUser getLoggedInUser(Principal principal){
        Optional<SecurityUser> userByUsername = userService.findUserByUsername(principal.getName());
        return userByUsername
                .orElseThrow(()->new NoSuchUser("No Such User with username "+ principal.getName()+" found!",principal.getName()));
    }


    private List<String> getRolesOfLoggedInUser(Principal principal){
        List<String> roles = getLoggedInUser(principal)
                .getSecurityRoles()
                .stream()
                .map(SecurityRole::getRoleName)
                .collect(Collectors.toList());

        if (roles.contains("admin")) {
            return Stream.of("admin" ,"user").collect(Collectors.toList());
        }
        else return Stream.of("user").collect(Collectors.toList());

    }


    private SecurityRole toSecRole(RoleEntity userEntity) {

        return SecurityRole.builder()
                .roleName(userEntity.getRoleName())
                .description(userEntity.getDescription())
                .id(userEntity.getId())
                .build();
    }
}
