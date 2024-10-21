package com.wiki.models.topic.dtos;

import com.wiki.models.note.dtos.NoteMapper;
import com.wiki.models.topic.entities.Topic;
import com.wiki.models.user.dtos.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {NoteMapper.class, UserMapper.class})
public interface TopicMapper {

   TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

   TopicDTOBasicResponse topicToBasicDTO(Topic topic);
   TopicDTOBasicNotesResponse topicToBasicNotesDTO(Topic topic);

}
