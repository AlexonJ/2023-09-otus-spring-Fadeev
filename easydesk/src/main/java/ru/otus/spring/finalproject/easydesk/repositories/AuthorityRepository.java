package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.finalproject.easydesk.models.db.RolesAuthorities;
import ru.otus.spring.finalproject.easydesk.models.enums.Roles;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<RolesAuthorities, Long> {

    List<RolesAuthorities> findAllByRole(Roles role);

}
