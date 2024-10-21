package com.wiki.models.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserDTORegister(
      @NotBlank String name,
      @NotBlank String lastName,
      @Email String email,
      @NotBlank String password,
      @Size(min = 1, max = 3, message = "El usuario debe tener minimo 1 rol, maximo 3") List<String> roles) {
}
