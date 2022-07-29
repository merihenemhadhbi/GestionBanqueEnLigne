package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.UtilisateurRepository;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	UtilisateurRepository utilisateurRepository;
	@Autowired
	LdapUtilisateurService ldapUtilisateurService;
	@Override
	public Utilisateur addUtilisateur(Utilisateur utilisateur) {
		ldapUtilisateurService.create(utilisateur);
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		ldapUtilisateurService.update(utilisateur);
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public void deleteUtilisateur(String username) {
		Utilisateur user = utilisateurRepository.findById(username).isPresent()?utilisateurRepository.findById(username).get():null;
		ldapUtilisateurService.delete(user);
		utilisateurRepository.deleteById(username);
	}

	@Override
	public List<Utilisateur> findAllUtilisateur() {
		return (List<Utilisateur>) utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur findUtilisateurById(String username) {
		return utilisateurRepository.findById(username).get();
	}

}
