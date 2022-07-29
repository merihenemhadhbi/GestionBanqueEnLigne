package tn.esprit.banque.scheduler;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import tn.esprit.banque.model.Compte;
import tn.esprit.banque.repository.CompteRepository;

public class ScheduledTasks {

	@Autowired
	CompteRepository cmptRepo;
	Logger logger = Logger.getLogger(ScheduledTasks.class.getName());

	@Scheduled(cron = "0 0 12 1 1/3 ? *")
	public void scheduleTaskUsingCronExpression() {
		List<Compte> comptes = cmptRepo.findAll();
		comptes.forEach(compte -> {
			compte.setSoldeCompte(compte.getSoldeCompte().subtract(new BigDecimal("30")));
		});
		cmptRepo.saveAll(comptes);
		logger.fine("Fees job passed!");
	}
}
