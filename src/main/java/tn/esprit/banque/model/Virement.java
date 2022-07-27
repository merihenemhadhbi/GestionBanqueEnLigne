package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Virement")
public class Virement extends Operation {

	@Column(name = "num_compte_recepteur")
	private long num_compte_recepteur;
	@Column(name = "montant")
	private long montant;
	@Column(name = "date_virement")
	private Date Date_virement;

	public Virement( Date date_operation, Date date_valeur, String statut, String commentaire,
			tn.esprit.banque.model.Compte compte) {
		super( date_operation, date_valeur, statut, commentaire, compte);
		// TODO Auto-generated constructor stub
	}
	public Virement() {
		super();
		// TODO Auto-generated constructor stub
	}
}
