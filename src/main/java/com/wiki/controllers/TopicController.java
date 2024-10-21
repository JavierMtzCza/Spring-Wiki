package com.wiki.controllers;

import com.wiki.models.topic.dtos.TopicDTOBasicNotesResponse;
import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.entities.Topic;
import com.wiki.services.topic.TopicService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

   @Autowired
   private TopicService topicService;

//   @GetMapping("/{name}/notes")
//   public ResponseEntity<?> getNotes(@PathVariable String name) {
//      TopicDTOBasicNotesResponse response = topicService.getTopicNotes(name);
//      return ResponseEntity.ok().body(response);
//   }

   @PostMapping("/create")
   public ResponseEntity<?> createTopic(@RequestBody Topic topic) {
      TopicDTOBasicResponse response = topicService.createTopic(topic);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }


}
