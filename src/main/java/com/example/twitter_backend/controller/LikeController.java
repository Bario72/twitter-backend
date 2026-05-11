package com.example.twitter_backend.controller;

import com.example.twitter_backend.dto.request.DislikeRequest;
import com.example.twitter_backend.dto.request.LikeRequest;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.service.LikeService;
import com.example.twitter_backend.service.TweetService;
import com.example.twitter_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final TweetService tweetService;


    public LikeController(LikeService likeService
                        ,UserService userService
                        ,TweetService tweetService){
        this.likeService = likeService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@Valid @RequestBody LikeRequest likeRequest){
        User user = userService.findById(likeRequest.getUserId());
        Tweet tweet = tweetService.findById(likeRequest.getTweetId());


        if(!likeService.isLiked(user ,tweet)){
            likeService.toggleLike(user , tweet);
        }
    }

    @PostMapping("/dislike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dislike(@Valid @RequestBody DislikeRequest dislikeRequest){
        User user = userService.findById(dislikeRequest.getUserId());
        Tweet tweet = tweetService.findById(dislikeRequest.getTweetId());

        if (likeService.isLiked(user , tweet)){
            likeService.toggleLike(user,tweet);
        }
    }
}
