package tn.esprit.banque.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.EntrySourceException;
import com.unboundid.ldif.LDIFEntrySource;
import com.unboundid.ldif.LDIFReader;
import com.unboundid.ldif.LDIFWriter;

import tn.esprit.banque.model.Employee;
import tn.esprit.banque.model.Morale;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.LdapUtilisateurRepository;

@Service
public class LdapUtilisateurService implements LdapUtilisateurRepository {

	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public List<String> getAllUtilisateurNames() {
		return ldapTemplate.search(query().where("objectclass").is("person"), new PersonNameAttributesMapper());
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
			switch ((String) attrs.get("ou").get()) {
			case "physique":
				Physique utilisateur = new Physique();
				utilisateur.setUsername(null != attrs.get("uid") ? (String) attrs.get("uid").get() : null);
				utilisateur.setNom(null != attrs.get("sn") ? (String) attrs.get("sn").get() : null);
				utilisateur.setPrenom(null != attrs.get("givenName") ? (String) attrs.get("givenName").get() : null);
				utilisateur.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
				return utilisateur;
			case "morale":
				Morale morale = new Morale();
				morale.setUsername(null != attrs.get("uid") ? (String) attrs.get("uid").get() : null);
				morale.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
				morale.setMatricule_Fiscale(null != attrs.get("cn") ? (String) attrs.get("cn").get() : null);
				morale.setNum_registe_commerce(null != attrs.get("sn") ? (String) attrs.get("sn").get() : null);
				morale.setNom(null != attrs.get("givenName") ? (String) attrs.get("givenName").get() : null);

				return morale;
			default:
				Employee employee = new Employee();
				employee.setEmail(null != attrs.get("mail") ? (String) attrs.get("mail").get() : null);
				employee.setNom(null != attrs.get("sn") ? (String) attrs.get("sn").get() : null);
				employee.setPrenom(null != attrs.get("givenName") ? (String) attrs.get("givenName").get() : null);
				employee.setAdresse(
						null != attrs.get("postalAddress") ? (String) attrs.get("postalAddress").get() : null);
				return employee;
			}

		}
	}

	private class PersonNameAttributesMapper implements AttributesMapper<String> {
		public String mapFromAttributes(Attributes attrs) throws NamingException {
			return attrs.get("cn").get().toString();
		}
	}

	public void create(Utilisateur user) {
		Name dn = buildDn(user);

		Attributes attributes = buildAttributes(user);
		ldapTemplate.bind(dn, null, attributes);
		Entry entry = buildEntry(dn, user);
		try {
			Resource resource = resourceLoader.getResource("classpath:/users.ldif");
			Map<String, Entry> list = getAllEntry(resource,user);
			OutputStream output = new FileOutputStream(resource.getFile());
			LDIFWriter ldifWriter = new LDIFWriter(output);
			list.values().forEach(c -> {
				try {
					ldifWriter.writeEntry(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			ldifWriter.writeEntry(entry);
			ldifWriter.flush();
			ldifWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(Utilisateur user) {
		Name dn = buildDn(user);
		Attributes attributes = buildAttributes(user);
		ldapTemplate.rebind(dn, null, attributes);
		Entry entry = buildEntry(dn, user);
		try {
			Resource resource = resourceLoader.getResource("classpath:/users.ldif");
			Map<String, Entry> list = getAllEntry(resource,user);
			OutputStream output = new FileOutputStream(resource.getFile());
			LDIFWriter ldifWriter = new LDIFWriter(output);
			list.values().forEach(c -> {
				try {
					if(c.getDN().equals(entry.getDN())) {
						ldifWriter.writeEntry(entry);
					}
					else
						ldifWriter.writeEntry(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			ldifWriter.flush();
			ldifWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(Utilisateur user) {
		Name dn =buildDn(user);
		ldapTemplate.unbind(dn);
		Resource resource = resourceLoader.getResource("classpath:/users.ldif");
		try {
			Map<String, Entry> list = getAllEntry(resource,user);
			OutputStream output = new FileOutputStream(resource.getFile());
			LDIFWriter ldifWriter = new LDIFWriter(output);
			list.remove(dn.toString() + ",dc=esprit,dc=tn");
			list.values().forEach(c -> {
				try {
					if(c.getAttribute("objectclass").hasValue("groupOfNames")) {
						c.removeAttributeValue("member", dn.toString() + ",dc=esprit,dc=tn");
					}
					ldifWriter.writeEntry(c);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			ldifWriter.flush();
			ldifWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Name buildDn(Utilisateur user) {
		LdapNameBuilder builder = LdapNameBuilder.newInstance();
		if (user instanceof Physique) {
			builder.add("ou", "physique");
		} else if (user instanceof Morale) {
			builder.add("ou", "morale");
		} else
			builder.add("ou", "employee");
		builder.add("uid", user.getUsername());
		return builder.build();

	}

	private Attributes buildAttributes(Utilisateur user) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocAttr = new BasicAttribute("objectclass");
		ocAttr.add("top");
		ocAttr.add("person");
		ocAttr.add("organizationalPerson");
		ocAttr.add("inetOrgPerson");
		attrs.put(ocAttr);
		attrs.put("uid", user.getUsername());
		attrs.put("userPassword", passwordEncoder.encode(user.getPassword()));
		attrs.put("mail", user.getEmail());
		if (user instanceof Physique) {
			attrs.put("ou", "physique");
			attrs.put("sn", ((Physique) user).getNom());
			attrs.put("cn", ((Physique) user).getPrenom() + ((Physique) user).getNom());
			attrs.put("givenName", ((Physique) user).getPrenom());
		}
		else if (user instanceof Morale) {
			attrs.put("ou", "morale");
			attrs.put("sn", ((Morale) user).getNum_registe_commerce());
			attrs.put("cn", ((Morale) user).getMatricule_Fiscale());
			attrs.put("givenName", ((Morale) user).getNom());
		}
		else {
			attrs.put("ou", "employee");
			attrs.put("sn", ((Employee) user).getNom());
			attrs.put("cn", ((Employee) user).getPrenom() + ((Employee) user).getNom());
			attrs.put("givenName", ((Employee) user).getPrenom());
		}
		return attrs;
	}

	public Entry buildEntry(Name dn, Utilisateur user) {
		Entry entry = new Entry(dn.toString() + ",dc=esprit,dc=tn");
		entry.addAttribute("objectclass", "top");
		entry.addAttribute("objectclass", "person");
		entry.addAttribute("objectclass", "organizationalPerson");
		entry.addAttribute("objectclass", "inetOrgPerson");
		entry.addAttribute("uid", user.getUsername());
		entry.addAttribute("userPassword", passwordEncoder.encode(user.getPassword()));
		entry.addAttribute("mail", user.getEmail());
		if (user instanceof Physique) {
			entry.addAttribute("ou", "physique");
			entry.addAttribute("sn", ((Physique) user).getNom());
			entry.addAttribute("cn", ((Physique) user).getPrenom() + ((Physique) user).getNom());
			entry.addAttribute("givenName", ((Physique) user).getPrenom());
		}
		else if (user instanceof Morale) {
			entry.addAttribute("ou", "morale");
			entry.addAttribute("sn", ((Morale) user).getNum_registe_commerce());
			entry.addAttribute("cn", ((Morale) user).getMatricule_Fiscale());
			entry.addAttribute("givenName", ((Morale) user).getNom());
		}
		else  {
			entry.addAttribute("ou", "employee");
			entry.addAttribute("sn", ((Employee) user).getNom());
			entry.addAttribute("cn", ((Employee) user).getPrenom() + ((Employee) user).getNom());
			entry.addAttribute("givenName", ((Employee) user).getPrenom());
		}
		return entry;
	}

	private Map<String, Entry> getAllEntry(Resource resource,Utilisateur user) throws IOException {
		Map<String, Entry> list = new LinkedHashMap<String, Entry>();
		LDIFEntrySource entrySource = new LDIFEntrySource(new LDIFReader(resource.getFile().getAbsolutePath()));
		try {
			while (true) {
				try {
					Entry entry = entrySource.nextEntry();
					if (entry != null) {
						if(entry.getAttribute("objectclass").hasValue("groupOfNames")) {
							String userDN = buildDn(user).toString() + ",dc=esprit,dc=tn";
							if(user instanceof Physique && entry.getAttribute("cn").hasValue("physique")) {
								entry.addAttribute("member",userDN);
							}
							else if(user instanceof Morale && entry.getAttribute("cn").hasValue("morale")) {
								entry.addAttribute("member",userDN);
							}
							else if (user instanceof Employee && entry.getAttribute("cn").hasValue("agent")) {
								entry.addAttribute("member",userDN);
							}
						}
						list.put(entry.getDN(), entry);
					} else
						break;
				} catch (EntrySourceException e) {
					// Some kind of problem was encountered (e.g., a malformed entry
					// found in the LDIF file, or an I/O error when trying to read). See
					// if we can continue reading entries.
					if (!e.mayContinueReading()) {
						break;
					}
				}
			}
		} finally {
			entrySource.close();
		}
		return list;
	}

}
