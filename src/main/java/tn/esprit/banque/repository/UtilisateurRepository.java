package tn.esprit.banque.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.banque.model.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {
	
	@Query("select u from Utilisateur u where  u.enabled = true ")
	Iterable<Utilisateur> findAllenabled();
	
	@Query("select u from Utilisateur u where  u.enabled = false ")
	Iterable<Utilisateur> findAlldisabled();
}
