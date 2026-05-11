package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Test
    void toggleLike_deletesExistingLikeWhenAlreadyLiked() {
        User user = new User();
        Tweet tweet = new Tweet();

        when(likeRepository.existsByUserAndTweet(user, tweet)).thenReturn(true);

        likeService.toggleLike(user, tweet);

        verify(likeRepository).deleteByUserAndTweet(user, tweet);
        verify(likeRepository, never()).save(any());
    }

    @Test
    void toggleLike_createsLikeWhenNotLikedYet() {
        User user = new User();
        Tweet tweet = new Tweet();

        when(likeRepository.existsByUserAndTweet(user, tweet)).thenReturn(false);

        likeService.toggleLike(user, tweet);

        verify(likeRepository).save(any());
        verify(likeRepository, never()).deleteByUserAndTweet(user, tweet);
    }
}
