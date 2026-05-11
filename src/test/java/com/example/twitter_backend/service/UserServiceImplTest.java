package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_persistsUserWithoutChangingPassword() {
        User user = new User();
        user.setPassword("alreadyEncodedOrPlain");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.save(user);

        assertEquals("alreadyEncodedOrPlain", saved.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void findByNickName_trimsInputAndReturnsUser() {
        User user = new User();
        user.setNickName("usera");

        when(userRepository.findByNickName("usera")).thenReturn(Optional.of(user));

        User found = userService.findByNickName("  usera  ");
        assertEquals("usera", found.getNickName());
    }

    @Test
    void findById_throwsWhenMissing() {
        when(userRepository.findById(55L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findById(55L));
    }
}
