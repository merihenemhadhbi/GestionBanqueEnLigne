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
	private String num_carte;
	@Column(name = "date_expiration")
	private Date date_expiration;
	@Column(name = "cvv")
	private String cvv;
	 @ManyToOne
	    @JoinColumn(name = "numeroCompte")
	    private Compte Compte;
}
