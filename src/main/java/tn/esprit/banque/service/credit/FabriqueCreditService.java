package tn.esprit.banque.service.credit;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Credits;

public interface FabriqueCreditService {
	CreditAbstractionService generateCredit(Credits.TypeCredit credit)  throws InvalidSwitchCaseException;
}
