package com.wiki.models.topic.entities;

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
@Table(name = "topics")
public class Topic {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String name;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User createdBy;

   @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Note> notes;

   @Column(name = "created_date")
   private LocalDateTime createdDate;  // Campo de fecha de creación

   @Column(name = "last_update")
   private LocalDateTime lastUpdate;    // Campo de última actualización

   @PrePersist
   public void createTopicDate() {
      LocalDateTime date = LocalDateTime.now();
      this.createdDate = date;
      this.lastUpdate = date;
   }

   public Topic() {
      this.notes = new ArrayList<>();
   }
}
