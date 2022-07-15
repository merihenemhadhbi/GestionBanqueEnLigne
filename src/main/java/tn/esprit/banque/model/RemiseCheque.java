package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RemiseCheque")
public class RemiseCheque extends Operation{
	@Column(name = "num_cheque")
	private long num_cheque;
	@Column(name = "cin")
	private String cin;
	@Column(name = "date_remise")
	private Date Date_remise;
}	

