package tn.esprit.banque.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.DecimalMin;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Credit")
public class Credits implements Serializable  {
	    public enum TypeCredit {
	        IMMOBILIER,CONSOMMATION;
	    }

	    @Id
		@GeneratedValue(strategy = GenerationType.AUTO)

	    private Long idCredit;

	    @DecimalMin(value = "0.0",message = "Veuillez specifier un Montant superieure ou egale à zero")
	    private BigDecimal montantCredit;

	    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
	    @Temporal(TemporalType.DATE)
	    private Date dateCredit;

	    @JsonProperty(access =JsonProperty.Access.READ_ONLY)
	    private BigDecimal mensualite;

	    @NotNull(message = "Veuillez preciser le type de credit")
	    @Enumerated(EnumType.STRING)
	    private TypeCredit typeCredit;

	    @JsonProperty(access =JsonProperty.Access.READ_ONLY)
	    private Long nombreMensualitesCredit;

	    @JsonProperty(access =JsonProperty.Access.READ_ONLY)
	    private BigDecimal montantReste;

	    @JsonProperty(access =JsonProperty.Access.READ_ONLY)
	    private BigDecimal montantReglee;

	    @ManyToOne
	    @JoinColumn(name = "numeroCompte")
	    @JsonIgnore
	    private Compte compteCredit;

	    /**
	     * Constructeur Credit
	     * @param idCredit
	     * @param montantCredit
	     * @param dateCredit
	     * @param mensualite
	     * @param typeCredit
	     * @param nombreMensualitesCredit
	     * @param montantReste
	     * @param montantReglee
	     */

	    public Credits(Long idCredit, BigDecimal montantCredit, Date dateCredit, BigDecimal mensualite, TypeCredit typeCredit, Long nombreMensualitesCredit, BigDecimal montantReste, BigDecimal montantReglee) {
	        this.idCredit = idCredit;
	        this.montantCredit = montantCredit;
	        this.dateCredit = dateCredit;
	        this.mensualite = mensualite;
	        this.typeCredit = typeCredit;
	        this.nombreMensualitesCredit = nombreMensualitesCredit;
	        this.montantReste = montantReste;
	        this.montantReglee = montantReglee;
	    }

	    public Credits(){

	    }

	    public Long getIdCredit() {
	        return idCredit;
	    }

	    public void setIdCredit(Long idCredit) {
	        this.idCredit = idCredit;
	    }

	    public BigDecimal getMontantCredit() {
	        return montantCredit;
	    }

	    public void setMontantCredit(BigDecimal montantCredit) {
	        this.montantCredit = montantCredit;
	    }

	    public Date getDateCredit() {
	        return dateCredit;
	    }

	    public void setDateCredit(Date dateCredit) {
	        this.dateCredit = dateCredit;
	    }

	    public BigDecimal getMensualite() {
	        return mensualite;
	    }

	    public void setMensualite(BigDecimal mensualite) {
	        this.mensualite = mensualite;
	    }

	    public TypeCredit getTypeCredit() {
	        return typeCredit;
	    }

	    public void setTypeCredit(TypeCredit typeCredit) {
	        this.typeCredit = typeCredit;
	    }

	    public Long getNombreMensualitesCredit() {
	        return nombreMensualitesCredit;
	    }

	    public void setNombreMensualitesCredit(Long nombreMensualitesCredit) {
	        this.nombreMensualitesCredit = nombreMensualitesCredit;
	    }

	    public BigDecimal getMontantReste() {
	        return montantReste;
	    }

	    public void setMontantReste(BigDecimal montantReste) {
	        this.montantReste = montantReste;
	    }

	    public BigDecimal getMontantReglee() {
	        return montantReglee;
	    }

	    public void setMontantReglee(BigDecimal montantReglee) {
	        this.montantReglee = montantReglee;
	    }

	    public Compte getCompteCredit() {
	        return compteCredit;
	    }

	    public void setCompteCredit(Compte compteCredit) {
	        this.compteCredit = compteCredit;
	    }
}
