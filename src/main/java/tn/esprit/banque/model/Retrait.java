package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Retrait")
public class Retrait extends Operation {
	@Column(name = "montant")
	private long montant;
	@Column(name = "date_retrait")
	private Date Date_retrait;
}
