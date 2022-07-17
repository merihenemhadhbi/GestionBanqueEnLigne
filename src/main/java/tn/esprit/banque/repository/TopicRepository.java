package tn.esprit.gbanque.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gbanque.model.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long>{

}
