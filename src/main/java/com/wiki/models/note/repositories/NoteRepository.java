package com.wiki.models.note.repositories;

import com.wiki.models.note.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

   Optional<Note> findNoteByTitle(String title);
}
