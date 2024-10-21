package com.wiki.models.note.repositories;

import com.wiki.models.note.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
