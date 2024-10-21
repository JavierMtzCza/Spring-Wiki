package com.wiki.services.note;

import com.wiki.models.note.dtos.NoteDTOBasicResponse;
import com.wiki.models.note.dtos.NoteDTOContentResponse;
import com.wiki.models.note.entities.Note;

public interface NoteService {

   Note getNoteByTitle(String name);

   NoteDTOBasicResponse createNote(Note note, String topicName);

   NoteDTOContentResponse getNoteContent(String title);
}
