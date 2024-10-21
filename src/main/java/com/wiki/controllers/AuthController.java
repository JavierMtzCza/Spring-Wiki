package com.wiki.controllers;

import com.wiki.models.user.dtos.UserDTOLoginRequest;
import com.wiki.models.user.dtos.UserDTORegisterRequest;
import com.wiki.models.user.dtos.UserDTOTokenResponse;
import com.wiki.services.user.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
   @Autowired
   private UserAuthService userAuthService;

   @PostMapping("/signIn")
   public ResponseEntity<?> signIn(@RequestBody @Valid UserDTORegisterRequest userDTORegister) {
      UserDTOTokenResponse response = userAuthService.createUser(userDTORegister);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PostMapping("/logIn")
   public ResponseEntity<?> logIn(@RequestBody @Valid UserDTOLoginRequest userDTOLogin) {
      UserDTOTokenResponse response = userAuthService.loginUser(userDTOLogin);
      return ResponseEntity.ok().body(response);
   }
}
