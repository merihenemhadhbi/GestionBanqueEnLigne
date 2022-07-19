package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.Compte;

public interface CompteService {
	Compte addCompte(Compte Compte);

	Compte updateCompte(Compte Compte);

	void deleteCompte(Long id);

	List<Compte> findAllComptes();

	Compte findCompteById(Long id);
}
