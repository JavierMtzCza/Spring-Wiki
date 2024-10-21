package com.wiki.models.role.repositories;

import com.wiki.models.role.entities.Role;
import com.wiki.models.role.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Set<Role> findAllByNameIn(List<String> roleNames);
}
