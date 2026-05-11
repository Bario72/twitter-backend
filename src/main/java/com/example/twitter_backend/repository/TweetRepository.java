package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TweetRepository  extends JpaRepository<Tweet , Long> {

    // Tweetleri yeniden eskiye sıralı getir
    List<Tweet> findAllByUserOrderByCreatedAtDesc(User user);

    // Tüm tweetleri tarihe göre sırala
    List<Tweet> findAllByOrderByCreatedAtDesc();
}
