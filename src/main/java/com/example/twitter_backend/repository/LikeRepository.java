package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.Like;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like , Long> {

    // Kullanıcı tweeti daha önce beğenmiş mi?
    Boolean existsByUserAndTweet(User user ,Tweet tweet);

    void deleteByUserAndTweet(User user, Tweet tweet);
}
