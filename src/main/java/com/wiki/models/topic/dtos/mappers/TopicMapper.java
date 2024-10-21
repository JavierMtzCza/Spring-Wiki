package com.wiki.models.topic.dtos.mappers;

import com.wiki.models.note.dtos.mappers.NoteMapper;
import com.wiki.models.topic.dtos.TopicDTOBasicNotesResponse;
import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.entities.Topic;
import com.wiki.models.user.dtos.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {NoteMapper.class, UserMapper.class})
public interface TopicMapper {

   TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

   TopicDTOBasicResponse topicToBasicDTO(Topic topic);
   TopicDTOBasicNotesResponse topicToBasicNotesDTO(Topic topic);

}
