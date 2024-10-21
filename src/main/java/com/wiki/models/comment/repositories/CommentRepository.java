package com.wiki.models.comment.repositories;

import com.wiki.models.comment.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

   // Obtener los comentarios base (sin parent)
   Page<Comment> findByNote_TitleAndParentCommentIsNull(String noteTitle, Pageable pageable);

   // Obtener las replies de un comentario específico por su ID
   Page<Comment> findByParentComment_Id(Long parentId, Pageable pageable);
}
