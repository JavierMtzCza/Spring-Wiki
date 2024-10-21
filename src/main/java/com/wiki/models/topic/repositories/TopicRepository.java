package com.wiki.models.topic.repositories;

import com.wiki.models.note.entities.Note;
import com.wiki.models.topic.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

   // Buscar un tópico por nombre
   Optional<Topic> findTopicByName(String name);

   // Obtener las notas asociadas a un tópico por su nombre, con paginación
   Page<Note> findNotesByName(String topicName, Pageable pageable);
}
