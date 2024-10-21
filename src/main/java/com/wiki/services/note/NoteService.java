package com.wiki.services.note;

import com.wiki.models.note.dtos.NoteDTOBasicResponse;
import com.wiki.models.note.dtos.NoteDTOContentResponse;
import com.wiki.models.note.entities.Note;
import com.wiki.models.topic.dtos.TopicDTOBasicNotesResponse;
import org.springframework.data.domain.Page;

public interface NoteService {

   Note getNoteByTitle(String name);

   NoteDTOBasicResponse createNote(Note note, String topicName);

   NoteDTOContentResponse getNoteContent(String title);

   Page<NoteDTOBasicResponse> getNotesByTopicName(String topicName, int page, int size);
//
}
