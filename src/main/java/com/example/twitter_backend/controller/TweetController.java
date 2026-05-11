package com.example.twitter_backend.controller;

import com.example.twitter_backend.dto.request.TweetCreateRequest;
import com.example.twitter_backend.dto.request.TweetUpdateRequest;
import com.example.twitter_backend.dto.response.TweetResponse;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.exception.ForbiddenException;
import com.example.twitter_backend.service.TweetService;
import com.example.twitter_backend.service.UserService;
import com.example.twitter_backend.util.mapper.TweetMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;
    private final TweetMapper tweetMapper;

    public TweetController (TweetService tweetService , UserService userService,TweetMapper tweetMapper){
        this.tweetService = tweetService;
        this.userService = userService ;
        this.tweetMapper = tweetMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponse create(@Valid @RequestBody TweetCreateRequest request){
        User user = userService.findById(request.getUserId());

        Tweet tweet = new Tweet();
        tweet.setContent(request.getContent().trim());
        tweet.setUser(user);

        Tweet saved = tweetService.save(tweet);
        return tweetMapper.toResponse(saved);
    }

    @GetMapping("/findByUserId")
    public List<TweetResponse> findByUserId(@RequestParam Long userId,
                                            @RequestParam(required = false) Long viewerId){
        User user = userService.findById(userId);
        return tweetMapper.toResponseList(tweetService.findByUsername(user), viewerId);
    }

    @GetMapping("/feed")
    public List<TweetResponse> feed(@RequestParam(required = false) Long viewerId) {
        return tweetMapper.toResponseList(tweetService.findAll(), viewerId);
    }

    @GetMapping("/profile/{userId}")
    public List<TweetResponse> profile(@PathVariable Long userId,
                                       @RequestParam(required = false) Long viewerId) {
        User user = userService.findById(userId);
        return tweetMapper.toResponseList(tweetService.findByUsername(user), viewerId);
    }

    @GetMapping("/findById")
    public TweetResponse findById(@RequestParam Long id) {
        return tweetMapper.toResponse(tweetService.findById(id));
    }

    @PutMapping("/{id}")
    public TweetResponse update(@PathVariable Long id, @Valid @RequestBody TweetUpdateRequest request){
        Tweet tweet = tweetService.findById(id);

        if (!tweet.getUser().getId().equals(request.getUserId())){
            throw new ForbiddenException("Bu tweeti güncelleme yetkiniz yok");
        }
        tweet.setContent(request.getContent().trim());
        return  tweetMapper.toResponse(tweetService.save(tweet));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id , @RequestParam Long userId){
        Tweet tweet = tweetService.findById(id);

        if(!tweet.getUser().getId().equals(userId)){
            throw new ForbiddenException("Bu tweeti silme yetkiniz yok");
        }
        tweetService.delete(id);
    }
}
