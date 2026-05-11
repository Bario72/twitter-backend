package com.example.twitter_backend.repository;

import com.example.twitter_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {

    Optional<Role> findByAuthority(String authority);
}
