package tn.esprit.banque.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Versement;

public interface OperationRepository extends CrudRepository<Carte, Long> {

	@Query("select o from Operation o where o.compte.codeCompte =:x order by o.dateOperation desc")
	public Page<Operation> listOperation(@Param("x")Long cpt);
	
	

}
