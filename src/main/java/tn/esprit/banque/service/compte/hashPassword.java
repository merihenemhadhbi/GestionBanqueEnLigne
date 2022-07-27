package tn.esprit.banque.service.compte;

import tn.esprit.banque.exceptions.InvalidHashPasswordException;

public interface hashPassword {
	public String hashPassword( final String password ) throws InvalidHashPasswordException;

}
