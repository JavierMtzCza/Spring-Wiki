package com.wiki.models.file.repositories;


import com.wiki.models.file.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
