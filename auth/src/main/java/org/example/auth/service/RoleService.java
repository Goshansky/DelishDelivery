package org.example.auth.service;


import lombok.RequiredArgsConstructor;
import org.example.auth.entities.Role;
import org.example.auth.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
