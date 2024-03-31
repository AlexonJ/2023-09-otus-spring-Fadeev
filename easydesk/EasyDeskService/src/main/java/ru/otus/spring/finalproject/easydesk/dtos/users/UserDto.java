package ru.otus.spring.finalproject.easydesk.dtos.users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
