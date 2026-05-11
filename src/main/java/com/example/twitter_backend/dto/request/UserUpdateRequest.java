package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @Size(min = 3, max = 100, message = "Nick name 3–100 karakter olmalı")
    private String nickName;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email(message = "Geçerli bir email girin")
    @Size(max = 50)
    private String email;

    @Size(max = 255)
    private String bio;
}
