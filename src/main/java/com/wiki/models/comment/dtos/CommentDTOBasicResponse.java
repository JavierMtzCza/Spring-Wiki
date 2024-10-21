package com.wiki.models.comment.dtos;

import com.wiki.models.user.dtos.UserDTOBasicResponse;

import java.time.LocalDateTime;

public record CommentDTOBasicResponse(
      Long id,
      String content,
      UserDTOBasicResponse createdBy,
      LocalDateTime createdDate,
      LocalDateTime lastUpdate,
      Long parentCommentId
) {
}
