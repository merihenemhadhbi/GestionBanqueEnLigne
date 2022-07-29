package tn.esprit.banque.model;


import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Physique")
public class Physique extends Utilisateur {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7140960393776824299L;
	
	@Column(name = "cin")
	private String cin;
	@Column(name = "nom")
	private String nom;
	@Column(name = "prenom")
	private String prenom;
	@Column(name = "salaire")
	private String salaire;
	@Column(name = "societe")
	private String societe;
	@Column(name = "civilite")
	private String civilite; 
	@Column(name = "nationalite")
	private String nationalite;
	@Column(name = "nb_enfant")
	private int nb_enfant;
	
	
	public Physique() {
		super();
	}
	
	
	public Physique(String username,String password,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		super(username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
	}


	public Physique(String email, String cin, String nom, String prenom) {
		super(email);
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public Physique(String username, String email, String cin, String nom, String prenom,String password) {
		super(username, password, true, true, true, false);
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	
	public Physique(String email, String email2, String cin, String nom, String prenom, String salaire, String societe,
			String civilite, String nationalite,int nb_enfant) {
		super(email);
		email = email2;
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.salaire = salaire;
		this.societe = societe;
		this.civilite = civilite;
		this.nationalite = nationalite;
		this.nb_enfant = nb_enfant;
	}


	public int getNb_enfant() {
		return nb_enfant;
	}


	public void setNb_enfant(int nb_enfant) {
		this.nb_enfant = nb_enfant;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
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
	public String getSalaire() {
		return salaire;
	}
	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cin == null) ? 0 : cin.hashCode());
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
		Physique other = (Physique) obj;
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
		if (cin == null) {
			if (other.cin != null)
				return false;
		} else if (!cin.equals(other.cin))
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
