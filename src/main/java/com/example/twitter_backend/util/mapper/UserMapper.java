package com.example.twitter_backend.util.mapper;

import com.example.twitter_backend.dto.response.UserResponse;
import com.example.twitter_backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user){
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNickName(user.getNickName());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());

        return response;
    }

    public List<UserResponse> toResponseList(List<User> users){
        if (users == null || users.isEmpty()){
            return Collections.emptyList();
        }
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
