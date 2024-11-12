package org.example.auth.service;

import org.example.auth.model.Role;
import org.example.auth.model.User;
import org.example.auth.repository.RoleRepository;
import org.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ServiceResult<User> register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ServiceResult<>(false, "Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
        return new ServiceResult<>(true, "User registered successfully", user);
    }

}
