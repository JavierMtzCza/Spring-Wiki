package com.wiki.models.note.dtos;

import com.wiki.models.note.entities.Note;
import com.wiki.models.user.dtos.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class})
public interface NoteMapper {

   NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

   NoteDTOBasicResponse noteToBasicDTO(Note note);

   NoteDTOContentResponse noteToContentDTO(Note note);

}
