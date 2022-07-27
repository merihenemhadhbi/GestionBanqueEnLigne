package tn.esprit.banque.service.credit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.model.Credits.TypeCredit;
@Service
public class FabriqueCreditServiceImpl implements FabriqueCreditService {
    private ApplicationContext applicationContext;
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

	@Override
	public CreditAbstractionService generateCredit(TypeCredit credit) throws InvalidSwitchCaseException {
CreditAbstractionService creditAbstractionService; 
switch (credit){
case IMMOBILIER:
	creditAbstractionService =  applicationContext.getBean(CreditImmobilierService.class);
    break;
case CONSOMMATION:
	creditAbstractionService = applicationContext.getBean(CreditConsommationService.class);
    break;
default:
    throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
}
return creditAbstractionService;
	}


	}


