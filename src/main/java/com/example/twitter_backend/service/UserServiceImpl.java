package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.User;
import com.example.twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        // Sifre hash'i yalnizca kayit (AuthController) tarafinda yapilir;
        // burada tekrar encode etmek cift hash olur ve login basarisiz olur.
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Kullancı bulunamadı! ID: " + id));

    }

    @Override
    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName.trim())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı nickName: " + nickName));
    }

    @Override
    public void delete(Long id) {
    User user = findById(id);
    userRepository.delete(user);
    }
}
