package com.example.twitter_backend.service;

import com.example.twitter_backend.entity.Role;
import com.example.twitter_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority)
                .orElseThrow(()-> new RuntimeException("Rol bulunamadı:" + authority));

    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
