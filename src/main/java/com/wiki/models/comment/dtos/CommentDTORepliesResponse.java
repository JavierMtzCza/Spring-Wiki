package com.wiki.models.comment.dtos;

import com.wiki.models.user.dtos.UserDTOBasicResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDTORepliesResponse(
      Long id,
      String content,
      UserDTOBasicResponse createdBy,
      LocalDateTime createdDate,
      LocalDateTime lastUpdate,
      Long parentCommentId,
      List<CommentDTORepliesResponse> replies
) {
}
