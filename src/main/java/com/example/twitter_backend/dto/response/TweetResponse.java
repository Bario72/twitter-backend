package com.example.twitter_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private String userNickName;
    private Integer likeCount;
    private Integer commentCount;
    private Integer retweetCount;
    private Boolean likedByViewer;
    private Boolean retweetedByViewer;
}
