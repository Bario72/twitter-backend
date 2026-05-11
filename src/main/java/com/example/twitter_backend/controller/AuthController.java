package com.example.twitter_backend.controller;

import com.example.twitter_backend.dto.request.LoginRequest;
import com.example.twitter_backend.dto.request.RegisterRequest;
import com.example.twitter_backend.dto.response.LoginResponse;
import com.example.twitter_backend.dto.response.UserResponse;
import com.example.twitter_backend.entity.Role;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.exception.BadRequestException;
import com.example.twitter_backend.repository.UserRepository;
import com.example.twitter_backend.service.RoleService;
import com.example.twitter_backend.service.UserService;
import com.example.twitter_backend.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          UserRepository userRepository,
                          RoleService roleService,
                          UserMapper userMapper,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse reqister(@Valid @RequestBody RegisterRequest request){
        if (userRepository.existsByNickName(request.getNickName())){
            throw  new BadRequestException("Bu nickName zaten kullanılıyor.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Bu email zaten kullanılıyor.");
        }

        User user = new User();
        user.setNickName(request.getNickName().trim());
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(request.getEmail().toLowerCase());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBio(request.getBio());


        Role role;
        try {
            role = roleService.findByAuthority("ROLE_USER");
        } catch (Exception ex){
            role = new Role();
            role.setAuthority("ROLE_USER");
            role = roleService.save(role);
        }
        user.addRole(role);

        User saved = userService.save(user);
        return userMapper.toResponse(saved);
        }

        @PostMapping("/login")
        public LoginResponse login(@Valid @RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(()-> new BadRequestException("Email veya Şifre hatalı"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Email veya şifre hatalı!");
        }
        return new LoginResponse(null,userMapper.toResponse(user));
        }
}
