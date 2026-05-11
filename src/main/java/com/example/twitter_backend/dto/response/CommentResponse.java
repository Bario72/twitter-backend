package com.example.twitter_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;

    private Long tweetId;

    private  String content;

    private String userNickName;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
