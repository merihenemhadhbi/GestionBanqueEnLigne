package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Carte")
public class Carte {
	@Id
	@Column(name = "num_carte")
	private Long num_carte;
	@Column(name = "date_expiration")
	private Date date_expiration;
	@Column(name = "cvv")
	private String cvv;
	 @ManyToOne
	    @JoinColumn(name = "numeroCompte")
	    private Compte compte;

	public Carte(Long num_carte,Date date_expiration, String cvv,	Compte compte) {
	super();
	this.num_carte = num_carte;
	this.date_expiration = date_expiration;
	this.cvv = cvv;
	this.compte = compte;
}
public Carte() {}

	public Long getNum_carte() {
		return this.num_carte;
	}

	public void setNum_carte(Long num_carte) {
		this.num_carte = num_carte;
	}

	public Date getDate_expiration() {
		return this.date_expiration;
	}

	public void setDate_expiration(Date date_expiration) {
		this.date_expiration = date_expiration;
	}

	public String getCvv() {
		return this.cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Compte getCompte() {
		return this.compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	


		
}
