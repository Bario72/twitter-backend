package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Retweet;
import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.exception.ForbiddenException;
import com.example.twitter_backend.exception.ResourceNotFoundException;
import com.example.twitter_backend.repository.RetweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetweetServiceImplTest {

    @Mock
    private RetweetRepository retweetRepository;

    @InjectMocks
    private RetweetServiceImpl retweetService;

    @Test
    void deleteByIdWithOwnerCheck_deletesWhenOwnerMatches() {
        User owner = new User();
        owner.setId(7L);

        Retweet retweet = new Retweet();
        retweet.setId(20L);
        retweet.setUser(owner);

        when(retweetRepository.findById(20L)).thenReturn(Optional.of(retweet));

        retweetService.deleteByIdWithOwnerCheck(20L, 7L);

        verify(retweetRepository).delete(retweet);
    }

    @Test
    void deleteByIdWithOwnerCheck_throwsForbiddenWhenOwnerDoesNotMatch() {
        User owner = new User();
        owner.setId(7L);

        Retweet retweet = new Retweet();
        retweet.setId(21L);
        retweet.setUser(owner);

        when(retweetRepository.findById(21L)).thenReturn(Optional.of(retweet));

        assertThrows(ForbiddenException.class, () -> retweetService.deleteByIdWithOwnerCheck(21L, 99L));
    }

    @Test
    void deleteByIdWithOwnerCheck_throwsNotFoundWhenRetweetMissing() {
        when(retweetRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> retweetService.deleteByIdWithOwnerCheck(404L, 1L));
    }
}
