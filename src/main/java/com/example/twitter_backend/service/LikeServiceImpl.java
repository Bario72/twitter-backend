package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Like;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeRepository likeRepository;

    @Override
    @Transactional
    public void toggleLike(User user, Tweet tweet) {
        if(likeRepository.existsByUserAndTweet(user, tweet)){
            likeRepository.deleteByUserAndTweet(user, tweet);
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setTweet(tweet);
            likeRepository.save(like);
        }
    }

    @Override
    public boolean isLiked(User user, Tweet tweet) {
        return likeRepository.existsByUserAndTweet(user, tweet);
    }
}
