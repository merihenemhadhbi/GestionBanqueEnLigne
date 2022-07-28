package tn.esprit.banque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee extends Utilisateur{
	@Column(name = "nom")
	private String nom;
	@Column(name = "prenom")
	private String prenom;
	@Id
	@Column(name = "email")
	private String email;
	
	public Employee() {
		super();
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
