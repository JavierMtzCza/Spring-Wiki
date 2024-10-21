package com.wiki.models.note.entities;

import com.wiki.models.comment.entities.Comment;
import com.wiki.models.file.entities.File;
import com.wiki.models.topic.entities.Topic;
import com.wiki.models.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "notes")
public class Note {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String title;
   private String description;

   @Column(columnDefinition = "TEXT")
   private String content;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User createdBy;

   @ManyToOne
   @JoinColumn(name = "topic_id") // La columna que hace referencia a Topic
   private Topic topic; // Este es el campo que se refiere a Topic

   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(
         name = "note_files",
         joinColumns = @JoinColumn(name = "note_id"),
         inverseJoinColumns = @JoinColumn(name = "file_id")
   )
   private List<File> files;

   @OneToMany(mappedBy = "note", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Comment> comments;

   @Column(name = "created_date")
   private LocalDateTime createdDate;  // Campo de fecha de creación

   @Column(name = "last_update")
   private LocalDateTime lastUpdate;    // Campo de última actualización


   @PrePersist
   public void createUserDate() {
      LocalDateTime date = LocalDateTime.now();
      this.createdDate = date;
      this.lastUpdate = date;
   }

   public Note() {
      this.comments = new ArrayList<>();
      this.files = new ArrayList<>();

   }
}
