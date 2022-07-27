package tn.esprit.banque.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

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
        	Physique utilisateur = new Physique();
        	utilisateur.setCin(null != attrs.get("cin") ? (String) attrs.get("cin").get() : null);
        	utilisateur.setNom((String) attrs.get("cn").get());
        	utilisateur.setPrenom((String) attrs.get("sn").get());
            utilisateur.setEmail(null != attrs.get("uid") ? (String) attrs.get("uid").get() : null);
            return utilisateur;
        }
    }
    private class PersonNameAttributesMapper implements AttributesMapper<String> {
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            return attrs.get("cn").get().toString();
        }
    }
}
