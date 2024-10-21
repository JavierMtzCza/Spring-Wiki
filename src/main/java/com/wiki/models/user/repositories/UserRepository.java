package com.wiki.models.user.repositories;

import com.wiki.models.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findUserByEmail(String email);

}
