package tn.esprit.banque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Physique")
public class Physique extends Utilisateur {
	@Id
	@Column(name = "email")
	private String email;

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


	public Physique(String email, String email2, String cin, String nom, String prenom) {
		super(email);
		email = email2;
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


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	
	
}
