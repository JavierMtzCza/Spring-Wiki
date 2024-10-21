package com.wiki.models.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTOLogin(@Email String email, @NotBlank String password) {
}
