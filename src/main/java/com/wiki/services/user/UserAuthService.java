package com.wiki.services.user;

import com.wiki.models.user.dtos.UserDTOLogin;
import com.wiki.models.user.dtos.UserDTORegister;
import com.wiki.models.user.dtos.UserDTOTokenResponse;

public interface UserAuthService {

   UserDTOTokenResponse createUser(UserDTORegister userData);

   UserDTOTokenResponse loginUser(UserDTOLogin userData);
}
