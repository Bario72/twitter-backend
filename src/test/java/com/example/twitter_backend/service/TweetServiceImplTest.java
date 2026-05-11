package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Test
    void findById_returnsTweetWhenExists() {
        Tweet tweet = new Tweet();
        tweet.setId(5L);
        tweet.setContent("hello");

        when(tweetRepository.findById(5L)).thenReturn(Optional.of(tweet));

        Tweet found = tweetService.findById(5L);
        assertEquals(5L, found.getId());
        assertEquals("hello", found.getContent());
    }

    @Test
    void findById_throwsWhenMissing() {
        when(tweetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tweetService.findById(99L));
    }

    @Test
    void delete_loadsThenDeletesTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(7L);

        when(tweetRepository.findById(7L)).thenReturn(Optional.of(tweet));

        tweetService.delete(7L);

        verify(tweetRepository).delete(tweet);
    }
}
