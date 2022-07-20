package tn.esprit.banque.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.banque.model.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

}
