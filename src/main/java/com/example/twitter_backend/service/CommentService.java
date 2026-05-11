package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Comment;
import com.example.twitter_backend.entity.Tweet;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    List<Comment> findByTweet(Tweet tweet);

    Comment updateContent(Long id, String newContent);

    void delete(Long id);

    void deleteWithAuthorization(Long commentId, Long actorUserId);
}
