package tn.esprit.banque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Morale")
public class Morale extends Utilisateur{
	
	@Column(name = "matricule_Fiscale")
	private String matricule_Fiscale;
	@Column(name = "capitale_Sociale")
	private String capitale_Sociale;
	@Column(name = "num_registe_commerce")
	private String num_registe_commerce;
	
	
	public Morale(String email, String matricule_Fiscale, String capitale_Sociale, String num_registe_commerce) {
		super(email);
		this.matricule_Fiscale = matricule_Fiscale;
		this.capitale_Sociale = capitale_Sociale;
		this.num_registe_commerce = num_registe_commerce;
	}
	
	public String getMatricule_Fiscale() {
		return matricule_Fiscale;
	}
	public void setMatricule_Fiscale(String matricule_Fiscale) {
		this.matricule_Fiscale = matricule_Fiscale;
	}
	public String getCapitale_Sociale() {
		return capitale_Sociale;
	}
	public void setCapitale_Sociale(String capitale_Sociale) {
		this.capitale_Sociale = capitale_Sociale;
	}
	public String getNum_registe_commerce() {
		return num_registe_commerce;
	}
	public void setNum_registe_commerce(String num_registe_commerce) {
		this.num_registe_commerce = num_registe_commerce;
	}
	
	
}
