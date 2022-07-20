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
	 public Utilisateur() {
			super();
		}
	public Utilisateur(String email) {
		super();
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNum_Tel() {
		return num_Tel;
	}
	public void setNum_Tel(String num_Tel) {
		this.num_Tel = num_Tel;
	}
	public List<Compte> getComptetList() {
		return comptetList;
	}
	public void setComptetList(List<Compte> comptetList) {
		this.comptetList = comptetList;
	}
	public List<Commentaire> getCommentairetList() {
		return commentairetList;
	}
	public void setCommentairetList(List<Commentaire> commentairetList) {
		this.commentairetList = commentairetList;
	}
	public List<Post> getPosttList() {
		return posttList;
	}
	public void setPosttList(List<Post> posttList) {
		this.posttList = posttList;
	}
	 
	 
}
