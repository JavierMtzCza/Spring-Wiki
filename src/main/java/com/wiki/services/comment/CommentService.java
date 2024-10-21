package com.wiki.services.comment;

import com.wiki.models.comment.dtos.CommentDTOBasicResponse;
import com.wiki.models.comment.dtos.CommentDTORepliesResponse;
import com.wiki.models.comment.entities.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

   Comment getCommentByID(Long id);

   Page<CommentDTOBasicResponse> getBaseCommentsByNoteTitle(String title, int page, int size);

   Page<CommentDTOBasicResponse> getRepliesByParentCommentId(Long parentId, int page, int size);

   CommentDTOBasicResponse addCommentToNote(Comment comment, String noteTitle);

   CommentDTOBasicResponse addReplyToComment(Comment reply, Long parentId);
}
