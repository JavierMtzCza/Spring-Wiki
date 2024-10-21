package com.wiki.models.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTOLoginRequest(@Email String email, @NotBlank String password) {
}
