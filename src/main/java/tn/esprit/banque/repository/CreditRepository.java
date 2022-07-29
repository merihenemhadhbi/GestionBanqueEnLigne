package tn.esprit.banque.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.banque.model.Credits;

@Repository
public interface CreditRepository extends CrudRepository<Credits, Long>{

}
