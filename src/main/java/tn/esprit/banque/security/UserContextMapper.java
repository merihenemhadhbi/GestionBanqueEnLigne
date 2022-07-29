package tn.esprit.banque.security;

import java.util.Collection;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.util.Assert;

import tn.esprit.banque.model.Employee;
import tn.esprit.banque.model.Morale;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.model.Utilisateur;

public class UserContextMapper implements UserDetailsContextMapper {

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		Attributes attrs = ctx.getAttributes();
			try {
				switch ((String)attrs.get("ou").get()) {
				case "physique":
					Physique utilisateur = new Physique(username, "[PROTECTED]", true, true, true, true);
					utilisateur.setAuthorities(authorities);
					return utilisateur;
				case "morale":
					Morale morale = new Morale(username, "[PROTECTED]", true, true, true, true);
					morale.setAuthorities(authorities);
					return morale;
				default:
					Employee employee = new Employee(username, "[PROTECTED]", true, true, true, true);
					employee.setAuthorities(authorities);
					return employee;
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return null;
	}
	


	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		Assert.isInstanceOf(Utilisateur.class, user, "UserDetails must be a Utilisateur instance");
		if (user instanceof Physique) {
			Physique p = (Physique) user;
			ctx.setAttributeValue("mail", p.getEmail());
			ctx.setAttributeValue("sn", p.getNom());
			ctx.setAttributeValue("givenName", p.getPrenom());
			ctx.setAttributeValue("postalAddress", p.getAdresse());
		}
		else if (user instanceof Morale) {
			Morale p = (Morale) user;
			ctx.setAttributeValue("mail", p.getEmail());
			ctx.setAttributeValue("description", p.getMatricule_Fiscale());
			ctx.setAttributeValue("title", p.getNum_registe_commerce());
			ctx.setAttributeValue("postalAddress", p.getAdresse());
		}
		else {
			Employee p = (Employee)user;
			ctx.setAttributeValue("mail", p.getEmail());
			ctx.setAttributeValue("sn", p.getNom());
			ctx.setAttributeValue("givenName", p.getPrenom());
			ctx.setAttributeValue("postalAddress", p.getAdresse());
		}
		
	}

}
