package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.Utilisateur;


public interface UtilisateurService {
	Utilisateur addUtilisateur(Utilisateur utilisateur);
	Utilisateur updateUtilisateur(Utilisateur utilisateur);
	void deleteUtilisateur(String email);
	List<Utilisateur> findAllUtilisateur();
	Utilisateur findUtilisateurById(String email);
}
