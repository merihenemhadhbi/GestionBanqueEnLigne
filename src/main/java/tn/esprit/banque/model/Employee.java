package tn.esprit.banque.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Employee")
public class Employee extends Utilisateur{
	
	@Column(name = "nom")
	private String nom;
	@Column(name = "prenom")
	private String prenom;
	
	public Employee() {
		super();
	}
	
	public Employee(String username ,String password,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		super(username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
	}
	
	public Employee(String email, String nom, String prenom, String email2) {
		super(email);
		this.nom = nom;
		this.prenom = prenom;
		email = email2;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (getAdresse() == null) {
			if (other.getAdresse() != null)
				return false;
		} else if (!getAdresse().equals(other.getAdresse()))
			return false;
		if (getEmail() == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!getEmail().equals(other.getEmail()))
			return false;
		if (getNum_Tel() == null) {
			if (other.getNum_Tel() != null)
				return false;
		} else if (!getNum_Tel().equals(other.getNum_Tel()))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}
	
	
}
