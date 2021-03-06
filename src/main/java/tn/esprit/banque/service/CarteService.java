package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;

public interface CarteService {
	Carte addCarte(Carte Carte);

	Carte updateCarte(Carte Carte);

	void deleteCarte(Long id);

	List<Carte> findAllCartes();

	Carte findCarteById(Long id);
	
	List<Carte> findCarteByCompte(Compte compte);

}
