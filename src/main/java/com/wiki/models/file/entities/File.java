package com.wiki.models.file.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "files")
public class File {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String fileName;
   private String fileType;

   private String filePath;

   @Column(unique = true)
   private String hash;
}
