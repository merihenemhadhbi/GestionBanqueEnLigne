package tn.esprit.banque.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "RetraitATM")
public class RetraitATM {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_retrait;
	@Column(name = "montant")
	private long montant;
	@Column(name = "date_retrait")
	private Date Date_retrait;
	 @ManyToOne
	    @JoinColumn(name = "numeroCarte")
	    private Carte carte;
	public RetraitATM() {
		super();
	}
	public RetraitATM(Long id_retrait, long montant, Date date_retrait, Carte carte) {
		super();
		this.id_retrait = id_retrait;
		this.montant = montant;
		Date_retrait = date_retrait;
		this.carte = carte;
	}
	public Long getid_retrait() {
		return id_retrait;
	}
	public long getMontant() {
		return montant;
	}
	public void setMontant(long montant) {
		this.montant = montant;
	}
	public Date getDate_retrait() {
		return Date_retrait;
	}
	public void setDate_retrait(Date date_retrait) {
		Date_retrait = date_retrait;
	}
	public Carte getCarte() {
		return carte;
	}
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	

}
