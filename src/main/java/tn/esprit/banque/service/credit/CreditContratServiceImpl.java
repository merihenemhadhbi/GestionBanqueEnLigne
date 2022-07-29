package tn.esprit.banque.service.credit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidBalanceException;
import tn.esprit.banque.exceptions.InvalidCreditException;
import tn.esprit.banque.exceptions.InvalidPayementException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.compte.CompteContrat;

@Service
public class CreditContratServiceImpl implements CreditContratService
{

	private CreditRepository creditRepository;
    private CompteContrat compteContrat;

    @Autowired
    public void setCompteContrat(CompteContrat compteContrat) {
        this.compteContrat = compteContrat;
    }

    @Autowired
    public void setCreditsRepository(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

	
	}


