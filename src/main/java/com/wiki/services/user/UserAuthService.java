package com.wiki.services.user;

import com.wiki.models.user.dtos.UserDTOLoginRequest;
import com.wiki.models.user.dtos.UserDTORegisterRequest;
import com.wiki.models.user.dtos.UserDTOTokenResponse;

public interface UserAuthService {

   UserDTOTokenResponse createUser(UserDTORegisterRequest userData);

   UserDTOTokenResponse loginUser(UserDTOLoginRequest userData);
}
