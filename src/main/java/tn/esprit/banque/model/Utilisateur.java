package tn.esprit.banque.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Utilisateur {
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "adresse")
	private String adresse;	

	@Column(name = "num_Tel")
	private String num_Tel;
	
	 @OneToMany(mappedBy = "numeroCompte")
	    private List<Compte> comptetList = new ArrayList<>();
	 @OneToMany(mappedBy = "id_commentaire")
	    private List<Commentaire> commentairetList = new ArrayList<>();
	 @OneToMany(mappedBy = "id_post")
	    private List<Post> posttList = new ArrayList<>();
}
