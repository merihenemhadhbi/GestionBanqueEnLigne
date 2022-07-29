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
	@Override
	public Utilisateur addUtilisateur(Utilisateur utilisateur) {
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public void deleteUtilisateur(String email) {
		utilisateurRepository.deleteById(email);
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
