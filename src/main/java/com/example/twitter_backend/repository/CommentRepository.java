package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.Comment;
import com.example.twitter_backend.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {

    // Belirli bir tweete yapılmış yorumlar
    List<Comment> findAllByTweetOrderByCreatedAtDesc(Tweet tweet);
}
