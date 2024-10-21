package com.wiki.models.note.dtos;

import com.wiki.models.user.dtos.UserDTOBasicResponse;

import java.time.LocalDateTime;

public record NoteDTOContentResponse(
      String title,
      String description,
      String content,
      UserDTOBasicResponse createdBy,
      LocalDateTime createdDate,
      LocalDateTime lastUpdate
) {
}
