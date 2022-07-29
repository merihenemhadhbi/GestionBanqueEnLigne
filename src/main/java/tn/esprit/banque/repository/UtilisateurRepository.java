package tn.esprit.banque.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.banque.model.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

	Map<String,Utilisateur> userCash = new HashMap<String,Utilisateur>();
	
	public default Map<String,Utilisateur> getUserCash() {
		if(UtilisateurRepository.userCash.isEmpty()) {
			findAll().forEach(c -> UtilisateurRepository.userCash.put(c.getUsername(), c));
		}
		return UtilisateurRepository.userCash;
	}
}
