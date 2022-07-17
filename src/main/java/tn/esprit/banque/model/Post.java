package tn.esprit.banque.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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
	    private Utilisateur utilisateur;
	 @OneToMany
	    private List<Commentaire> Commentaire;
	 
	
	public Post(long id_post, String titre, String contenu, Date date_creation, Date date_modification,
			Utilisateur utilisateur, List<tn.esprit.banque.model.Commentaire> commentaire) {
		super();
		this.id_post = id_post;
		this.titre = titre;
		this.contenu = contenu;
		this.date_creation = date_creation;
		this.date_modification = date_modification;
		this.utilisateur = utilisateur;
		Commentaire = commentaire;
	}

	public Post() {}
	
	public long getId_post() {
		return id_post;
	}

	public void setId_post(long id_post) {
		this.id_post = id_post;
	}

	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDate_creation() {
		return date_creation;
	}
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}
	public Date getDate_modification() {
		return date_modification;
	}
	public void setDate_modification(Date date_modification) {
		this.date_modification = date_modification;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public List<Commentaire> getCommentaire() {
		return Commentaire;
	}
	public void setCommentaire(List<Commentaire> commentaire) {
		Commentaire = commentaire;
	}
	 
	 
}
