package com.example.twitter_backend.controller;

import com.example.twitter_backend.dto.request.CommentCreateRequest;
import com.example.twitter_backend.dto.request.CommentUpdateRequest;
import com.example.twitter_backend.dto.response.CommentResponse;
import com.example.twitter_backend.entity.Comment;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.service.CommentService;
import com.example.twitter_backend.service.TweetService;
import com.example.twitter_backend.service.UserService;
import com.example.twitter_backend.util.mapper.CommentMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

        private final CommentService commetService;
        private final TweetService tweetService;
        private final UserService userService;
        private final CommentMapper commentMapper;


       public CommentController(CommentService commetService
                                , TweetService tweetService
                                , UserService userService
                                , CommentMapper commentMapper){

           this.commetService = commetService;
           this.tweetService = tweetService;
           this.userService = userService;
           this.commentMapper = commentMapper;
       }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
   public CommentResponse create(@Valid @RequestBody CommentCreateRequest request){
        Tweet tweet = tweetService.findById(request.getTweetId());
        User user   = userService.findById(request.getUserId());

        Comment comment = new Comment();
        comment.setContent(request.getContent().trim());
        comment.setTweet(tweet);
        comment.setUser(user);

        return commentMapper.toResponse(commetService.save(comment));
    }

    @GetMapping("/byTweet")
    public java.util.List<CommentResponse> findByTweet(@RequestParam Long tweetId){
        Tweet tweet = tweetService.findById(tweetId);
        return commentMapper.toResponseList(commetService.findByTweet(tweet));
    }

    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id , @Valid @RequestBody CommentUpdateRequest request ){

           Comment updated  = commetService.updateContent(id , request.getContent());
           return commentMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestParam Long userId){
           commetService.deleteWithAuthorization(id, userId);
    }

}
