package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TweetServiceImpl implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Tweet> findByUsername(User user) {
        return tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public Tweet findById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tweet Bulunamadı!"));
    }

    @Override
    public void delete(Long id) {
        Tweet tweet  = findById(id);
        tweetRepository.delete(tweet);
    }
}
