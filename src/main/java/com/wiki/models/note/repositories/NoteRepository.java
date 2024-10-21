package com.wiki.models.note.repositories;

import com.wiki.models.note.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

   Optional<Note> findNoteByTitle(String title);

   // Obtener notas por el nombre del tópico con paginación
   Page<Note> findByTopic_Name(String topicName, Pageable pageable);
}
