package tn.esprit.banque.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Morale")
public class Morale extends Utilisateur{
	
	@Column(name = "matricule_Fiscale")
	private String matricule_Fiscale;
	@Column(name = "capitale_Sociale")
	private String capitale_Sociale;
	@Column(name = "num_registe_commerce")
	private String num_registe_commerce;
	@Column(name = "nom")
	private String nom;
	
	
	public Morale() {
		super();
	}
	
	public Morale(String username, String password,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		super(username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
	}
	
	public Morale(String username, String email, String matricule_Fiscale, String nom, String num_registe_commerce,String password) {
		super(username, password, true, true, true, false);
		this.matricule_Fiscale = matricule_Fiscale;
		this.num_registe_commerce = num_registe_commerce;
		this.nom = nom;
	}

	public Morale(String email,String nom, String matricule_Fiscale, String capitale_Sociale, String num_registe_commerce) {
		super(email);
		this.matricule_Fiscale = matricule_Fiscale;
		this.capitale_Sociale = capitale_Sociale;
		this.num_registe_commerce = num_registe_commerce;
		this.nom = nom;
	}
	
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMatricule_Fiscale() {
		return matricule_Fiscale;
	}
	public void setMatricule_Fiscale(String matricule_Fiscale) {
		this.matricule_Fiscale = matricule_Fiscale;
	}
	public String getCapitale_Sociale() {
		return capitale_Sociale;
	}
	public void setCapitale_Sociale(String capitale_Sociale) {
		this.capitale_Sociale = capitale_Sociale;
	}
	public String getNum_registe_commerce() {
		return num_registe_commerce;
	}
	public void setNum_registe_commerce(String num_registe_commerce) {
		this.num_registe_commerce = num_registe_commerce;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((matricule_Fiscale == null) ? 0 : matricule_Fiscale.hashCode());
		result = prime * result + ((num_registe_commerce == null) ? 0 : num_registe_commerce.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Morale other = (Morale) obj;
		if (getAdresse() == null) {
			if (other.getAdresse() != null)
				return false;
		} else if (!getAdresse().equals(other.getAdresse()))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
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
		if (matricule_Fiscale == null) {
			if (other.matricule_Fiscale != null)
				return false;
		} else if (!matricule_Fiscale.equals(other.matricule_Fiscale))
			return false;
		if (num_registe_commerce == null) {
			if (other.num_registe_commerce != null)
				return false;
		} else if (!num_registe_commerce.equals(other.num_registe_commerce))
			return false;
		return true;
	}
	
	
}
