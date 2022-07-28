package tn.esprit.banque.service.credit;

import java.math.BigDecimal;
import java.util.List;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Credits;

public interface FabriqueCreditService {
	CreditAbstractionService generateCredit(Credits.TypeCredit credit) throws InvalidSwitchCaseException;

	Credits updateCredit(Credits credits);

	List<Credits> allCredits();

	Credits reglerUneMensualite(Long idCredit, BigDecimal mensualite);

	Credits findUnCredit(Long id);
}
