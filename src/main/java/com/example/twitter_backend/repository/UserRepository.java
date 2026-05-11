package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);


    Boolean existsByNickName(String nickName);

    Optional<User> findByEmail(String email);
}
