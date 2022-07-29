package tn.esprit.banque.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Versement;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

	
	
	
}
