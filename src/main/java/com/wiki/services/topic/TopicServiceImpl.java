package com.wiki.services.topic;

import com.wiki.models.note.dtos.NoteMapper;
import com.wiki.models.topic.dtos.TopicDTOBasicNotesResponse;
import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.dtos.TopicMapper;
import com.wiki.models.topic.entities.Topic;
import com.wiki.models.topic.repositories.TopicRepository;
import com.wiki.models.user.entities.User;
import com.wiki.services.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

   @Autowired
   private UserService userService;

   @Autowired
   private TopicRepository topicRepository;


   @Override
   @Transactional(readOnly = true)
   public Topic getTopicByName(String name) {
      return topicRepository.findTopicByName(name).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
   }

   @Override
   @Transactional
   public TopicDTOBasicResponse createTopic(Topic topic) {

      Optional<Topic> topicOptional = topicRepository.findTopicByName(topic.getName());

      if (topicOptional.isPresent())
         throw new EntityExistsException("Topic Name exist");

      String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByEmail(userEmail);

      topic.setCreatedBy(user);

      return TopicMapper.INSTANCE.topicToBasicDTO(topicRepository.save(topic));
   }

}
