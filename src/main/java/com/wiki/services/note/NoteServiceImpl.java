package com.wiki.services.note;

import com.wiki.models.note.dtos.NoteDTOBasicResponse;
import com.wiki.models.note.dtos.NoteDTOContentResponse;
import com.wiki.models.note.dtos.NoteMapper;
import com.wiki.models.note.entities.Note;
import com.wiki.models.note.repositories.NoteRepository;
import com.wiki.models.topic.dtos.TopicDTOBasicNotesResponse;
import com.wiki.models.topic.entities.Topic;
import com.wiki.models.user.entities.User;
import com.wiki.services.topic.TopicService;
import com.wiki.services.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteServiceImpl implements NoteService {

   @Autowired
   private TopicService topicService;

   @Autowired
   private UserService userService;

   @Autowired
   private NoteRepository noteRepository;

   @Override
   @Transactional(readOnly = true)
   public Note getNoteByTitle(String title) {
      return noteRepository.findNoteByTitle(title).orElseThrow(() -> new EntityNotFoundException("Note not found"));
   }

   @Override
   public NoteDTOBasicResponse createNote(Note note, String topicName) {

      if (noteRepository.findNoteByTitle(note.getTitle()).isPresent())
         throw new EntityExistsException("The note with that tile exist");

      //Obtenemos el usuario
      String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
      User user = userService.getUserByEmail(userEmail);

      //Obtenemos el topic
      Topic topic = topicService.getTopicByName(topicName);

      note.setCreatedBy(user);
      note.setTopic(topic);

      return NoteMapper.INSTANCE.noteToBasicDTO(noteRepository.save(note));
   }

   @Override
   @Transactional(readOnly = true)
   public NoteDTOContentResponse getNoteContent(String title) {
      Note note = getNoteByTitle(title);
      return NoteMapper.INSTANCE.noteToContentDTO(note);
   }

   @Override
   public Page<NoteDTOBasicResponse> getNotesByTopicName(String topicName, int page, int size) {
      PageRequest pageRequest = PageRequest.of(page, size);
      return noteRepository.findByTopic_Name(topicName, pageRequest)
            .map(NoteMapper.INSTANCE::noteToBasicDTO);
   }

}
