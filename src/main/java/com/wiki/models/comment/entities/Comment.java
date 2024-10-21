package com.wiki.models.comment.entities;

import com.wiki.models.note.entities.Note;
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
@Table(name = "comments")
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(columnDefinition = "TEXT")
   private String content;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User createdBy;

   @ManyToOne
   @JoinColumn(name = "note_id")
   private Note note;

   @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Comment> replies;

   @ManyToOne
   @JoinColumn(name = "parent_comment_id")
   private Comment parentComment;

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

   public Comment() {
      this.replies = new ArrayList<>();
   }
}
