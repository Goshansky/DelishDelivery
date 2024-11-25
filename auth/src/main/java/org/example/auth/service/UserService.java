package org.example.auth.service;

import org.example.auth.dtos.RegistrationUserDto;
import org.example.auth.dtos.UserDto;
import org.example.auth.entities.User;
import org.example.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setPhone(registrationUserDto.getPhone());
        user.setName(registrationUserDto.getName());
        user.setPatronymic(registrationUserDto.getPatronymic());
        user.setSurname(registrationUserDto.getSurname());
        user.setRoles(List.of(roleService.getUserRole()));
        user.setAddress(registrationUserDto.getAddress());
        return userRepository.save(user);
    }

    public UserDto getUserInfo(Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        UserDto userDto = new UserDto();
        userDto.setUsername(user.get().getUsername());
        userDto.setName(user.get().getName());
        userDto.setSurname(user.get().getSurname());
        userDto.setPatronymic(user.get().getPatronymic());
        userDto.setEmail(user.get().getEmail());
        userDto.setPhone(user.get().getPhone());
        userDto.setId(user.get().getId());
        userDto.setRoles(user.get().getRoles());
        userDto.setAddress(user.get().getAddress());

        return userDto;
    }
}
