package org.example.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.example.auth.dtos.UserDto;
import org.example.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> userData(Principal principal) {
        return ResponseEntity.ok(userService.getUserInfo(principal));
    }
}
