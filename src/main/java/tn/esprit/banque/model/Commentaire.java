package tn.esprit.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Commentaire")
public class Commentaire {
	   @Id
		@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id_commentaire")
	private long id_commentaire;
	@Column(name = "contenu")
	private String contenu;
	@Column(name = "date_creation")
	private Date date_creation;
	@Column(name = "date_modification")
	private Date date_modification;
	 @ManyToOne
	    @JoinColumn(name = "email")
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private Utilisateur utilisateur;
}
