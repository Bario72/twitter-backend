package com.example.twitter_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {

    private Long tweetId;

    private long likeCount;

    private boolean likedByCurrentUser;
}
