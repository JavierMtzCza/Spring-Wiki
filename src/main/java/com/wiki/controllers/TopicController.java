package com.wiki.controllers;

import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.entities.Topic;
import com.wiki.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {

   @Autowired
   private TopicService topicService;

   @PostMapping("/create")
   public ResponseEntity<?> createTopic(@RequestBody Topic topic) {
      TopicDTOBasicResponse response = topicService.createTopic(topic);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

}
