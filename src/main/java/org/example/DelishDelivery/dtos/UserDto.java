package org.example.DelishDelivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    public UserDto() {

    }
}
