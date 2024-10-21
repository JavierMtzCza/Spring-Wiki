package com.wiki.models.topic.dtos.mappers;

import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {
   TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

   @Mapping(source = "createdBy.email", target = "createdBy.email")
   @Mapping(source = "createdBy.name", target = "createdBy.name")
   @Mapping(source = "createdBy.lastName", target = "createdBy.lastName")
   TopicDTOBasicResponse toTopicDTO(Topic topic);

}
