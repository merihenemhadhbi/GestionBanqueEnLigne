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
	
}
