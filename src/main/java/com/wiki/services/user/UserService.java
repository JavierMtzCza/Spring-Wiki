package com.wiki.services.user;

import com.wiki.models.user.dtos.UserDTOLogin;
import com.wiki.models.user.dtos.UserDTORegister;
import com.wiki.models.user.dtos.UserDTOTokenResponse;
import com.wiki.models.user.entities.User;

import java.util.List;

public interface UserService {

   User getUserByEmail(String email);

   List<User> getAllUsers();

   UserDTOTokenResponse createUser(UserDTORegister userData);

   UserDTOTokenResponse loginUser(UserDTOLogin userData);
}
