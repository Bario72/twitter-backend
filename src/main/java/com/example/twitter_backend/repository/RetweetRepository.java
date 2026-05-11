package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.Retweet;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet,Long> {

    // Kullanıcı bu tweeti daha önce retweetlemiş mi?

    boolean existsByUserAndTweet(User user, Tweet tweet);

    void deleteByUserAndTweet(User user, Tweet tweet);
}
