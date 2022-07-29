package tn.esprit.banque.service.compte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAdminDeletionException;
import tn.esprit.banque.exceptions.InvalidConfirmationException;
import tn.esprit.banque.exceptions.InvalidHashPasswordException;
import tn.esprit.banque.exceptions.InvalidPasswordException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.repository.CompteRepository;

@Service
public class CompteContratImpl implements CompteContrat {
	private CompteRepository compteRepo;
	private HashPasswordContrat hashPasswordContrat;
	
	 @Autowired
	    public void setHashPasswordContrat(HashPasswordContrat hashPasswordContrat) {
	        this.hashPasswordContrat = hashPasswordContrat;
	    }

	    @Autowired
	    public void setCompteRepo(CompteRepository compteRepo) {
	        this.compteRepo = compteRepo;
	    }

	@Transactional
	@Override
	public Compte findLeCompte(Long id) throws InvalidAccountException {

		Compte compte = compteRepo.findById(id).orElseThrow(() -> new InvalidAccountException("Compte indisponible"));

		if (!compte.isEtatCompte()) {
			throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
		}

		return compte;
	}

	public Page<Compte> findCompteParMotCle(String mc, int page, int size) {
		return compteRepo.findCompteParMotCle(mc, PageRequest.of(page, size));
	}

	@Override
	public Compte updateAccount(Compte compte, Long aId) {
		compte.setNumeroCompte(aId);
		return compteRepo.save(compte);
	}

	@Override
	public List<Compte> allAccounts() {
		return compteRepo.findAll();

	}

	@Transactional
	@Override
	public Compte disactivateAccount(Long idCompte, String motDePasse)
			throws InvalidAccountException, InvalidPasswordException, InvalidConfirmationException,
			InvalidAdminDeletionException, InvalidHashPasswordException {

		Compte compte = findLeCompte(idCompte);

		System.out.println("****db**** Le mot de passe dans data base");
		System.out.println(compte.getMotDePasse());
		System.out.println("****received****");
		System.out.println("le mot de passe recu est :" + motDePasse);
		System.out.println("le mot de passe recu crypté:***");
		System.out.println(hashPasswordContrat.hashPassword(motDePasse));

		if (!compte.getMotDePasse().equals(hashPasswordContrat.hashPassword(motDePasse))) {
			throw new InvalidPasswordException("Mot de passe incorrect Veuillez saisir votre mot de passe");
		} else {
			compte.setEtatCompte(false);
		}

		return updateAccount(compte, compte.getNumeroCompte());
	}

}
