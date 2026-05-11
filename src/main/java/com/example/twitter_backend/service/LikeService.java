package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;

public interface LikeService {

    void toggleLike(User user, Tweet tweet);

    boolean isLiked(User user, Tweet tweet);
}
