package com.wiki.models.user.dtos;

import com.wiki.models.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

   UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

   @Mapping(target = "roles", ignore = true)
   @Mapping(target = "password", ignore = true)
   User userDTORegisterToUser(UserDTORegisterRequest userDTO);

   UserDTOBasicResponse userToBasicDTO(User user);
}
