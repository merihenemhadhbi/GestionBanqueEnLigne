package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Virement")
public class Virement  extends Operation{
	@Column(name = "num_compte_recepteur")
	private long num_compte_recepteur;
	@Column(name = "montant")
	private long montant;
	@Column(name = "date_virement")
	private Date Date_virement;
}
