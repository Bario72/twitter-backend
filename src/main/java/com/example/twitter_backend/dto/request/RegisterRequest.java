package com.example.twitter_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Nick name boş olamaz")
    @Size(min = 3, max = 100, message = "Nick name 3–100 karakter olmalı")
    private String nickName;

    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email girin")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8, max = 100, message = "Şifre en az 8 karakter olmalı")
    private String password;


    @Size(max = 255)
    private String bio;
}
