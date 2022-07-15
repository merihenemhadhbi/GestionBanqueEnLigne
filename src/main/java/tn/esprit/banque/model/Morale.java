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
}
