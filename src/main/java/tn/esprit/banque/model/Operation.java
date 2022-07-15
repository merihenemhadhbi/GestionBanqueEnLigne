package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Operation {
	 public enum statut {
	        VALIDER,WAITING,REJECTED;
	    }
	   @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id_operation;
	   @Column(name = "date_operation")
		private Date date_operation;
	   @Column(name = "date_valeur")
		private Date date_valeur;
	   @Column(name = "statut")
		private String statut;
	   @Column(name = "commentaire")
		private String commentaire;
	   
	   @ManyToOne
	    @JoinColumn(name = "numeroCompte")
	    private Compte Compte;
}
