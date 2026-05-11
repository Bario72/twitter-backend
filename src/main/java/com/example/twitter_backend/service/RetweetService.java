package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;

public interface RetweetService {

    void toggleRetweet(User user , Tweet tweet);

    boolean isRetweeted(User user,Tweet tweet);

    void deleteByIdWithOwnerCheck(Long retweetId, Long userId);
}
