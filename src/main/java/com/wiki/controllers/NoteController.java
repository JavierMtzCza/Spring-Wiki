package com.wiki.controllers;

import com.wiki.models.note.dtos.NoteDTOBasicResponse;
import com.wiki.models.note.dtos.NoteDTOContentResponse;
import com.wiki.models.note.entities.Note;
import com.wiki.services.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController {

   @Autowired
   private NoteService noteService;

   @GetMapping("/{title}/content")
   public ResponseEntity<?> getNoteContent(@PathVariable String title) {
      NoteDTOContentResponse response = noteService.getNoteContent(title);
      return ResponseEntity.ok().body(response);
   }

   @PostMapping("/{topicName}/create")
   public ResponseEntity<?> createNote(@RequestBody Note note, @PathVariable String topicName) {
      NoteDTOBasicResponse response = noteService.createNote(note, topicName);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }


}
