package tn.esprit.gbanque.service;

import java.util.List;

import tn.esprit.gbanque.model.Comment;

public interface CommentService {
	Comment addComment(Comment comment);

	Comment updateComment(Comment comment);

	void deleteComment(Long id);

	List<Comment> findAllComments();

	Comment findCommentById(Long id);
}
