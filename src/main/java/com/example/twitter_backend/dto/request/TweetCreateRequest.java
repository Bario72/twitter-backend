package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TweetCreateRequest {

    @NotBlank(message = "Tweet içeriği boş olamaz")
    @Size(max = 280, message = "En fazla 280 karakter")
    private String content;


    @NotNull(message = "Kullanıcı ID zorunlu")
    private Long userId;
}
