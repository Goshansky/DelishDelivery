package org.example.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.auth.entities.Role;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String name;
    private String surname; // Фамилия
    private String patronymic; // Отчество
    private Collection<Role> roles;
    private String address;

    public UserDto() {

    }
}
