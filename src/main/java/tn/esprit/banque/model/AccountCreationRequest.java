package tn.esprit.gbanque.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountCreationRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date requestDate;
	private String city;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private MaritalStatusEnum maritalStatus;
	private AccountTypeEnum type;
	private String identityJustification;
	private boolean canAccess;
	private StatusEnum status;
	@OneToOne(mappedBy = "accountCreationRequest")
	private Appointment appointment;
	
}
