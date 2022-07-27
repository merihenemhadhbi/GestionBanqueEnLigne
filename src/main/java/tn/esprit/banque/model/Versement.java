package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Versement")
public class Versement extends Operation {

	@Column(name = "montant")
	private long montant;
	@Column(name = "cin")
	private String cin;
	@Column(name = "nom_expediteur")
	private String nom_expediteur;

	public Versement( Date date_operation, Date date_valeur, String statut, String commentaire,
			tn.esprit.banque.model.Compte compte) {
		super( date_operation, date_valeur, statut, commentaire, compte);
		// TODO Auto-generated constructor stub
	}
	public Versement() {
		super();
		// TODO Auto-generated constructor stub
	}

}
