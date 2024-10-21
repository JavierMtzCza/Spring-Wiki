package com.wiki.models.comment.repositories;

import com.wiki.models.comment.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
