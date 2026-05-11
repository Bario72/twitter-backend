package com.example.twitter_backend.controller;


import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.service.RetweetService;
import com.example.twitter_backend.service.TweetService;
import com.example.twitter_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;
    private final UserService userService;
    private final TweetService tweetService;

    public RetweetController(RetweetService retweetService
                            ,UserService userService
                            ,TweetService tweetService) {

        this.retweetService = retweetService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void retweet(@RequestParam Long userId,@RequestParam Long tweetId){
        User user = userService.findById(userId);
        Tweet tweet = tweetService.findById(tweetId);
        retweetService.toggleRetweet(user,tweet);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unretweet(@PathVariable Long id, @RequestParam Long userId){
        retweetService.deleteByIdWithOwnerCheck(id, userId);
    }

}
