package org.example.DelishDelivery.service;


import lombok.RequiredArgsConstructor;
import org.example.DelishDelivery.entities.Role;
import org.example.DelishDelivery.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
