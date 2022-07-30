package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.UtilisateurRepository;
import tn.esprit.banque.security.RoleConstants;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	@Autowired
	UtilisateurRepository utilisateurRepository;
	@Autowired
	LdapUtilisateurService ldapUtilisateurService;

	@Override
	public Utilisateur addUtilisateur(Utilisateur utilisateur) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && (auth.getAuthorities().contains(RoleConstants.ROLE_ADMIN)
				|| auth.getAuthorities().contains(RoleConstants.ROLE_AGENT))) {
			utilisateur.setEnabled(true);
		}
		ldapUtilisateurService.create(utilisateur);
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && ((auth.getAuthorities().contains(RoleConstants.ROLE_ADMIN)
				|| auth.getAuthorities().contains(RoleConstants.ROLE_AGENT)))
				|| utilisateur.getUsername().equals(auth.getName())) {
			ldapUtilisateurService.update(utilisateur);
			return utilisateurRepository.save(utilisateur);
		}
		throw new AuthorizationServiceException("can't update others account");
	}

	@Override
	public void deleteUtilisateur(String username) {
		Utilisateur user = utilisateurRepository.findById(username).isPresent()
				? utilisateurRepository.findById(username).get()
				: null;
		ldapUtilisateurService.delete(user);
		utilisateurRepository.deleteById(username);
	}

	@Override
	public List<Utilisateur> findAllUtilisateur() {
		return (List<Utilisateur>) utilisateurRepository.findAll();
	}
	@Override
	public List<Utilisateur> findAllEnabledUtilisateur() {
		return (List<Utilisateur>) utilisateurRepository.findAllenabled();
	}
	
	@Override
	public List<Utilisateur> findAllDisabledUtilisateur() {
		return (List<Utilisateur>) utilisateurRepository.findAlldisabled();
	}

	@Override
	public Utilisateur findUtilisateurById(String username) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && ((auth.getAuthorities().contains(RoleConstants.ROLE_ADMIN)
				|| auth.getAuthorities().contains(RoleConstants.ROLE_AGENT)))
				|| username.equals(auth.getName())) {
			return utilisateurRepository.findById(username).get();
		}
		throw new AuthorizationServiceException("can't see others users");
	}
	
	@Override
	public Utilisateur triggerValidate(Utilisateur user){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && ((auth.getAuthorities().contains(RoleConstants.ROLE_ADMIN)
				|| auth.getAuthorities().contains(RoleConstants.ROLE_AGENT)))
				|| user.getUsername().equals(auth.getName())) {
			return utilisateurRepository.save(user);
		}
		throw new AuthorizationServiceException("can't disable others users");

	}

}
