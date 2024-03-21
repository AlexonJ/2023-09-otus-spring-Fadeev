package ru.otus.spring.finalproject.easydesk.dtos.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.finalproject.easydesk.models.enums.Roles;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Column
    private String username;

    @Column
    private String email;

}
