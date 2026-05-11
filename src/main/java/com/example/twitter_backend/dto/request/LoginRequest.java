package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email girin")
    @Size(max = 50)
    private String email;


    @NotBlank(message = "Şifre boş olamaz")
    @Size(max = 100)
    private String password;

}
