package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Comment;
import com.example.twitter_backend.entity.Tweet;
import com.example.twitter_backend.exception.ForbiddenException;
import com.example.twitter_backend.exception.ResourceNotFoundException;
import com.example.twitter_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommetServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByTweet(Tweet tweet) {
        return commentRepository.findAllByTweetOrderByCreatedAtDesc(tweet);
    }

    @Override
    public Comment updateContent(Long id, String newContent) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı! ID: " + id));
        if (newContent == null || newContent.isBlank()) {
            throw new IllegalArgumentException("Yorum içeriği boş olamaz");
        }
        comment.setContent(newContent.trim());
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteWithAuthorization(Long commentId, Long actorUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Yorum bulunamadı. ID: " + commentId));

        Long commentOwnerId = comment.getUser().getId();
        Long tweetOwnerId = comment.getTweet().getUser().getId();

        if (!actorUserId.equals(commentOwnerId) && !actorUserId.equals(tweetOwnerId)) {
            throw new ForbiddenException("Bu yorumu silme yetkiniz yok");
        }

        commentRepository.delete(comment);
    }
}
