package com.example.twitter_backend.controller;

import com.example.twitter_backend.dto.request.UserUpdateRequest;
import com.example.twitter_backend.dto.response.UserResponse;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.exception.BadRequestException;
import com.example.twitter_backend.repository.UserRepository;
import com.example.twitter_backend.service.UserService;
import com.example.twitter_backend.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

        private final UserService userService;
        private  final UserRepository userRepository;
        private final UserMapper userMapper;


    public UserController(UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id){
        return userMapper.toResponse(userService.findById(id));
    }
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id , @Valid @RequestBody UserUpdateRequest request){
        User user = userService.findById(id);


        if (request.getNickName() != null && !request.getNickName().isBlank()) {
            String nick = request.getNickName().trim();
            if (!nick.equals(user.getNickName()) && userRepository.existsByNickName(nick)) {
                throw new BadRequestException("Bu nickName zaten kullanılıyor");
            }
            user.setNickName(nick);
        }
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName().trim());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            String email = request.getEmail().trim().toLowerCase();
            userRepository.findByEmail(email).ifPresent(existing -> {
                if (!existing.getId().equals(user.getId())) {
                    throw new BadRequestException("Bu email zaten kullanılıyor");
                }
            });
            user.setEmail(email);
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio().trim());
        }
        User updated = userService.save(user);
        return userMapper.toResponse(updated);
    }
}
