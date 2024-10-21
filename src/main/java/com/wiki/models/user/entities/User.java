package com.wiki.models.user.entities;

import com.wiki.models.comment.entities.Comment;
import com.wiki.models.note.entities.Note;
import com.wiki.models.role.entities.Role;
import com.wiki.models.topic.entities.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @Column(name = "last_name")
   private String lastName;

   @Column(unique = true)
   private String email;
   private String password;

   @Column(name = "is_enabled")
   private boolean isEnabled;

   @Column(name = "account_no_locked")
   private boolean accountNoLocked;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //Eager, carga todos los roles que tiene
   @JoinTable(
         name = "user_roles",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id")
   )
   private Set<Role> roles;

   @OneToMany(mappedBy = "createdBy")
   private List<Topic> topics;

   @OneToMany(mappedBy = "createdBy")
   private List<Note> notes;

   @OneToMany(mappedBy = "createdBy")
   private List<Comment> comments;

   @PrePersist
   public void setControlValues(){
      this.isEnabled = true;
      this.accountNoLocked = true;
   }

   public User() {
      this.topics = new ArrayList<>();
      this.notes = new ArrayList<>();
      this.comments = new ArrayList<>();
      this.roles = new HashSet<>();
   }
}
