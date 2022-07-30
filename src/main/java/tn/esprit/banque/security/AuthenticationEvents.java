package tn.esprit.banque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.UtilisateurRepository;
import tn.esprit.banque.service.UtilisateurService;

@Component
public class AuthenticationEvents {
	@Autowired
	UtilisateurRepository userRepo;
	
	@EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
		Utilisateur user = (Utilisateur)success.getAuthentication().getPrincipal();
		Utilisateur dbuser = userRepo.findById(user.getUsername()).get();
		if(!dbuser.isEnabled()) {
			throw new DisabledException("user disabled");
		}
		if(dbuser==null) {
			userRepo.save(user);
			}
		else if(!dbuser.equals(user)) {
			user = updateUser(user,dbuser);
			userRepo.save(user);
			}
    }
	
	public Utilisateur updateUser(Utilisateur user ,Utilisateur dbuser) {
		dbuser.setAuthorities(user.getAuthorities());
		dbuser.setPassword("[PROTECTED]");
		dbuser.setAccountNonExpired(user.isAccountNonExpired());
		dbuser.setAccountNonLocked(user.isAccountNonLocked());
		dbuser.setEnabled(user.isEnabled());
		dbuser.setCredentialsNonExpired(user.isCredentialsNonExpired());
		return dbuser;
	}
	
	
}
