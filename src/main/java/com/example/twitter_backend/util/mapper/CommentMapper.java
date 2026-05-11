package com.example.twitter_backend.util.mapper;

import com.example.twitter_backend.dto.response.CommentResponse;
import com.example.twitter_backend.entity.Comment;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment){
        if (comment == null) {
            return null;
        }

        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());

        if (comment.getTweet() != null) {
            response.setTweetId(comment.getTweet().getId());

        }
        if (comment.getUser() != null){
            response.setUserId(comment.getUser().getId());
            response.setUserNickName(comment.getUser().getNickName());
        }
        return response;
    }

    public List<CommentResponse> toResponseList(List<Comment> comments){
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
