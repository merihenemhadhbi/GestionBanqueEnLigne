package tn.esprit.gbanque.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accountNumber;
	private String affiliate;
	private AccountTypeEnum type;
	private Date creationDate;
	private Date expirationDate;
	private Long balance;
	private String rib;
	private String identityJustification;
	private boolean canAccess;
	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions;
	@OneToMany(mappedBy = "account")
	private List<ConnectionRequest> connectionRequests;
	@JsonIgnore
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "account")
	private List<CreditRequest> creditRequests;
}
