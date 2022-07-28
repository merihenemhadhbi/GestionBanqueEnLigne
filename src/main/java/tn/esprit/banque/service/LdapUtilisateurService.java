package tn.esprit.banque.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Employee;
import tn.esprit.banque.model.Morale;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.LdapUtilisateurRepository;

@Service
public class LdapUtilisateurService  implements LdapUtilisateurRepository {
	@Autowired
    private LdapTemplate ldapTemplate;
    @Override
    public List<String> getAllUtilisateurNames() {
        return ldapTemplate.search(query().where("objectclass").is("person"),
                new PersonNameAttributesMapper());
    }
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return ldapTemplate.search(query().where("objectclass").is("person"), new PersonAttributesMapper());
    }
    @Override
    public Utilisateur getUtilisateurNamesByUid(String userId) {
        List<Utilisateur> people = ldapTemplate.search(query().where("uid").is(userId), new PersonAttributesMapper());
        return ((null != people && !people.isEmpty()) ? people.get(0) : null);
    }
    private class PersonAttributesMapper implements AttributesMapper<Utilisateur> {
        public Utilisateur mapFromAttributes(Attributes attrs) throws NamingException {
        	switch ((String)attrs.get("ou").get()) {
			case "physique":
				Physique utilisateur = new Physique();
	        	utilisateur.setCin(null != attrs.get("description") ? (String) attrs.get("description").get() : null);
	        	utilisateur.setNom(null != attrs.get("sn") ? (String) attrs.get("sn").get() : null);
	        	utilisateur.setPrenom(null != attrs.get("givenName") ? (String) attrs.get("givenName").get() : null);
	            utilisateur.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
	            utilisateur.setAdresse(null != attrs.get("postalAddress") ? (String) attrs.get("postalAddress").get() : null);
	            return utilisateur;
			case "morale":
				Morale morale = new Morale();
				morale.setAdresse(null != attrs.get("postalAddress") ? (String) attrs.get("postalAddress").get() : null);
				morale.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
				morale.setMatricule_Fiscale(null != attrs.get("description") ? (String) attrs.get("description").get() : null);
				morale.setNum_registe_commerce(null != attrs.get("title") ? (String) attrs.get("title").get() : null);
				return morale;
			default:
				Employee employee = new Employee();
				employee.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
				employee.setNom(null != attrs.get("sn") ? (String) attrs.get("sn").get() : null);
				employee.setPrenom(null != attrs.get("givenName") ? (String) attrs.get("givenName").get() : null);
				employee.setAdresse(null != attrs.get("postalAddress") ? (String) attrs.get("postalAddress").get() : null);
				return employee;
			}
        	
        }
    }
    private class PersonNameAttributesMapper implements AttributesMapper<String> {
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            return attrs.get("cn").get().toString();
        }
    }
}
