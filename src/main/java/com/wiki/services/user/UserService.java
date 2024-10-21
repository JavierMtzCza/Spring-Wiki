package com.wiki.services.user;

import com.wiki.models.user.entities.User;

import java.util.List;

public interface UserService {

   User getUserByEmail(String email);

   List<User> getAllUsers();

}
