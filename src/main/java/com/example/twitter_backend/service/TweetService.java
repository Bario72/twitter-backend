package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;

import java.util.List;

public interface TweetService {

    Tweet save(Tweet tweet);

    List<Tweet> findAll();

    List<Tweet> findByUsername(User user);

    Tweet findById(Long id);

    void delete(Long id);
}
