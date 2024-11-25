package org.example.auth.dtos;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phone;
    private String name;
    private String surname; // Фамилия
    private String patronymic; // Отчество
    private String address;
}
