package com.wiki.models.topic.dtos;

import com.wiki.models.user.dtos.UserDTOBasicResponse;

import java.time.LocalDateTime;

public record TopicDTOBasicResponse(
      Long id,
      String name,
      UserDTOBasicResponse createdBy,
      LocalDateTime createdDate,
      LocalDateTime lastUpdate
) {
}
