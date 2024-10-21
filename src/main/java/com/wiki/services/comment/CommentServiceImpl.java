package com.wiki.services.comment;

import com.wiki.models.comment.dtos.CommentDTOBasicResponse;
import com.wiki.models.comment.dtos.CommentDTORepliesResponse;
import com.wiki.models.comment.dtos.mappers.CommentMapper;
import com.wiki.models.comment.entities.Comment;
import com.wiki.models.comment.repositories.CommentRepository;
import com.wiki.models.note.entities.Note;
import com.wiki.models.user.entities.User;
import com.wiki.services.note.NoteService;
import com.wiki.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

   @Autowired
   private NoteService noteService;

   @Autowired
   private UserService userService;

   @Autowired
   private CommentRepository commentRepository;

   @Override
   @Transactional(readOnly = true)
   public Comment getCommentByID(Long id) {
      return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
   }

   @Override
   @Transactional(readOnly = true)
   public Page<CommentDTOBasicResponse> getBaseCommentsByNoteTitle(String title, int page, int size) {
      PageRequest pageRequest = PageRequest.of(page, size);
      return commentRepository.findByNote_TitleAndParentCommentIsNull(title, pageRequest)
            .map(CommentMapper.INSTANCE::commentToBasicDTO);
   }

   @Override
   @Transactional(readOnly = true)
   public Page<CommentDTOBasicResponse> getRepliesByParentCommentId(Long parentId, int page, int size) {
      PageRequest pageRequest = PageRequest.of(page, size);
      return commentRepository.findByParentComment_Id(parentId, pageRequest)
            .map(CommentMapper.INSTANCE::commentToBasicDTO);
   }


   @Override
   @Transactional
   public CommentDTOBasicResponse addCommentToNote(Comment comment, String noteTitle) {

      //Obtenemos el usuario
      User user = extracUserInContextHolder();
      //Obtenemos los datos de la nota
      Note note = noteService.getNoteByTitle(noteTitle);

      comment.setCreatedBy(user);
      comment.setNote(note);

      return CommentMapper.INSTANCE.commentToBasicDTO(commentRepository.save(comment));
   }

   @Override
   @Transactional
   public CommentDTOBasicResponse addReplyToComment(Comment reply, Long parentId) {

      //Obtenemos el usuario
      User user = extracUserInContextHolder();
      //Obtenemos el parent
      Comment parent = getCommentByID(parentId);

      reply.setCreatedBy(user);
      reply.setParentComment(parent);

      return CommentMapper.INSTANCE.commentToBasicDTO(commentRepository.save(reply));
   }

   private User extracUserInContextHolder() {
      String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
      return userService.getUserByEmail(userEmail);
   }


}
