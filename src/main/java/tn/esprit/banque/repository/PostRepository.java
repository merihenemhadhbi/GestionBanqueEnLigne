package tn.esprit.banque.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.banque.model.Post;
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {


}
