package com.wiki.models.user.dtos;

public record UserDTOBasicResponse(
      String email,
      String name,
      String lastName
) {
}
