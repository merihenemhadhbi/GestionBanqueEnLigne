package tn.esprit.gbanque.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String address;
	private String email;
	private String telephoneNumber;
	private String cin;
	private UserEnum type;
	@OneToMany(mappedBy = "user")
	private List<Account> accounts;
	@OneToMany(mappedBy = "user")
	private List<Alert> alerts;
	@OneToMany(mappedBy = "user")
	private List<Reclamation> reclamations;
	@OneToMany(mappedBy = "user")
	private List<Topic> topics;
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	@OneToMany(mappedBy = "user")
	private List<Evaluation> evaluations;
	@OneToMany(mappedBy = "user")
	private List<Notification> notifications;
}
