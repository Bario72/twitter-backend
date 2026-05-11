package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RetweetDeleteRequest {

    @NotNull(message = "Kullanıcı ID zorunlu")
    private Long userId;
}
