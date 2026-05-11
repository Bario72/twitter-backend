package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Role;

public interface RoleService {

    Role findByAuthority(String authority);

    Role save(Role role);
}
