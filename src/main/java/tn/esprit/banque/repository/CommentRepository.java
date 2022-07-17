package tn.esprit.gbanque.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.gbanque.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
