package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeRequest {

    @NotNull(message = "Tweet ID zorunlu")
    private Long tweetId;


    @NotNull(message = "Kullanıcı ID zorunlu")
    private Long userId;
}
