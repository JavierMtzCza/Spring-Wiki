package com.wiki.models.topic.dtos;

import com.wiki.models.note.dtos.NoteDTOBasicResponse;

import java.util.List;

public record TopicDTOBasicNotesResponse(
      String name,
      List<NoteDTOBasicResponse> notes
) {
}
