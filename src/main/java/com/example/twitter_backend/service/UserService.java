package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.User;

public interface UserService {

    User save(User user);

    User findById(Long id);

    User findByNickName(String nickName);

    void delete(Long id);
}
