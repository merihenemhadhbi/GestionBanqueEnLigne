package tn.esprit.banque.repository;

import java.util.List;

import tn.esprit.banque.model.Utilisateur;

public interface LdapUtilisateurRepository {
	public List<Utilisateur> getAllUtilisateurs();
    public List<String> getAllUtilisateurNames();
    public Utilisateur getUtilisateurNamesByUid(String userId);
}
