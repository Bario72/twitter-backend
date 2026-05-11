package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Retweet;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.exception.ForbiddenException;
import com.example.twitter_backend.exception.ResourceNotFoundException;
import com.example.twitter_backend.repository.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetweetServiceImpl implements RetweetService{
    @Autowired
    private RetweetRepository retweetRepository;

    @Override
    @Transactional
    public void toggleRetweet(User user, Tweet tweet) {
        if (retweetRepository.existsByUserAndTweet(user, tweet)){
            retweetRepository.deleteByUserAndTweet(user, tweet);
        } else {
            Retweet retweet = new Retweet();
            retweet.setUser(user);
            retweet.setTweet(tweet);
            retweetRepository.save(retweet);
        }
    }

    @Override
    public boolean isRetweeted(User user, Tweet tweet) {
        return retweetRepository.existsByUserAndTweet(user, tweet);
    }

    @Override
    @Transactional
    public void deleteByIdWithOwnerCheck(Long retweetId, Long userId) {
        Retweet retweet = retweetRepository.findById(retweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Retweet bulunamadı. ID: " + retweetId));
        if (!retweet.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Bu retweeti silme yetkiniz yok");
        }
        retweetRepository.delete(retweet);
    }

}

