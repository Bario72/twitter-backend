package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Comment;
import com.example.twitter_backend.repository.CommentRepository;
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
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommetServiceImpl commentService;

    @Test
    void updateContent_updatesCommentWhenInputIsValid() {
        Comment comment = new Comment();
        comment.setId(10L);
        comment.setContent("old");

        when(commentRepository.findById(10L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment updated = commentService.updateContent(10L, "  new content  ");

        assertEquals("new content", updated.getContent());
        verify(commentRepository).save(comment);
    }

    @Test
    void updateContent_throwsWhenContentIsBlank() {
        Comment comment = new Comment();
        comment.setId(11L);
        comment.setContent("old");

        when(commentRepository.findById(11L)).thenReturn(Optional.of(comment));

        assertThrows(IllegalArgumentException.class, () -> commentService.updateContent(11L, "   "));
    }

    @Test
    void updateContent_throwsWhenCommentNotFound() {
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentService.updateContent(99L, "hello"));
    }
}
