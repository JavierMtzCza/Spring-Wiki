package com.wiki.models.note.dtos;

import com.wiki.models.user.dtos.UserDTOBasicResponse;

import java.time.LocalDateTime;

public record NoteDTOBasicResponse(
      String title,
      String description,
      UserDTOBasicResponse createdBy,
      LocalDateTime createdDate,
      LocalDateTime lastUpdate
) {
}
