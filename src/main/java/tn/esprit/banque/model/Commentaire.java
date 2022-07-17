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
	 @ManyToOne
		private Post post;
	 
	 
	 
	public Commentaire(long id_commentaire, String contenu, Date date_creation, Date date_modification,
			Utilisateur utilisateur, Post post) {
		super();
		this.id_commentaire = id_commentaire;
		this.contenu = contenu;
		this.date_creation = date_creation;
		this.date_modification = date_modification;
		this.utilisateur = utilisateur;
		this.post = post;
	}
	public Commentaire() {}
	public long getId_commentaire() {
		return id_commentaire;
	}
	public void setId_commentaire(long id_commentaire) {
		this.id_commentaire = id_commentaire;
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
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	 
}
