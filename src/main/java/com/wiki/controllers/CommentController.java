package com.wiki.controllers;

import com.wiki.models.comment.dtos.CommentDTOBasicResponse;
import com.wiki.models.comment.dtos.CommentDTORepliesResponse;
import com.wiki.models.comment.entities.Comment;
import com.wiki.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

   @Autowired
   private CommentService commentService;

   @PostMapping("/add_to_note/{noteTitle}")
   public ResponseEntity<?> addCmmentToNote(@PathVariable String noteTitle, @RequestBody Comment comment) {
      CommentDTOBasicResponse response = commentService.addCommentToNote(comment, noteTitle);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PostMapping("/add_to_comment/{parentId}")
   public ResponseEntity<?> addReplyToComment(@PathVariable Long parentId, @RequestBody Comment reply) {
      CommentDTOBasicResponse response = commentService.addReplyToComment(reply, parentId);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   // Endpoint para obtener los comentarios base de una nota
   @GetMapping("/note/{noteTitle}")
   public Page<CommentDTOBasicResponse> getCommentsByNoteTitle(@PathVariable String noteTitle, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
      return commentService.getBaseCommentsByNoteTitle(noteTitle, page, size);
   }

   // Endpoint para obtener las replies de un comentario específico
   @GetMapping("/{parentId}/replies")
   public Page<CommentDTOBasicResponse> getRepliesByParentCommentId(@PathVariable Long parentId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
      return commentService.getRepliesByParentCommentId(parentId, page, size);
   }


}
