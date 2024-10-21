package com.wiki.models.comment.dtos.mappers;

import com.wiki.models.comment.dtos.CommentDTOBasicResponse;
import com.wiki.models.comment.dtos.CommentDTORepliesResponse;
import com.wiki.models.comment.entities.Comment;
import com.wiki.models.user.dtos.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class})
public interface CommentMapper {

   CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

   // Mapeamos el parentComment solo con su ID
   @Mapping(source = "parentComment.id", target = "parentCommentId")
   CommentDTOBasicResponse commentToBasicDTO(Comment comment);

}
