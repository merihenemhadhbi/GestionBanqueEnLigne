package tn.esprit.banque.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Post")
public class Post {
	@Id
	@Column(name = "id_post")
	private long id_post;
	@Column(name = "titre")
	private String titre;
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
	 @OneToMany
	   @JoinColumn(name = "id_commentaire")
	    private List<Commentaire> Commentaire;
}
