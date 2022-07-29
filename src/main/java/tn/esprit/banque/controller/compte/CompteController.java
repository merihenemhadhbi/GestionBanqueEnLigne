<<<<<<< HEAD
package tn.esprit.banque.controller.compte;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAdminDeletionException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidConfirmationException;
import tn.esprit.banque.exceptions.InvalidHashPasswordException;
import tn.esprit.banque.exceptions.InvalidPasswordException;
import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.exceptions.InvalidUserException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Compte.CategorieCompte;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.service.compte.CompteContrat;
import tn.esprit.banque.service.compte.CompteCourant;
import tn.esprit.banque.service.compte.CompteCreation;
import tn.esprit.banque.service.compte.CompteEpargne;
import tn.esprit.banque.service.compte.FabriqueCompte;
import tn.esprit.banque.service.compte.HashPasswordContrat;

@RestController
public class CompteController {

	@Autowired
	private FabriqueCompte fabriqueCompte;
	@Autowired
	private CompteContrat compteContrat;
	private HashPasswordContrat hashPasswordContrat;
	@Autowired
	private CompteRepository CompteRepo;

	@Autowired
	public void setHashPasswordContrat(HashPasswordContrat hashPasswordContrat) {
		this.hashPasswordContrat = hashPasswordContrat;
	}

	@PostMapping(value = "/AccountCreation", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> creationCompte(@Valid @RequestBody CompteCreation cmpt, BindingResult bindingResult) {
		Compte compte = new Compte();
		try {

			if (!cmpt.getMotDePasse().equals(cmpt.getReMotDePasse())) {
				throw new InvalidPasswordException(
						"Veuillez re saisir le meme mot de passe dans le champ reMotDePasse Pour la confirmation ");
			}
			compte.setSoldeCompte(cmpt.getSoldeCompte());
			compte.setMotDePasse(hashPasswordContrat.hashPassword(cmpt.getMotDePasse()));

			switch (cmpt.getTypecompte()) {
			case "COURANT":
				CompteCourant ct = (CompteCourant) fabriqueCompte.generateAccount(Compte.TypeCompte.COURANT);
				return new ResponseEntity<>(ct.createAccount(compte, (long) 1), HttpStatus.OK);
			case "EPARGNE":
				CompteEpargne ep = (CompteEpargne) fabriqueCompte.generateAccount(Compte.TypeCompte.EPARGNE);
				return new ResponseEntity<>(ep.createAccount(compte, (long) 2), HttpStatus.OK);
			default:
				throw new InvalidAccountException(
						"Entrez un type du compte valide en majiscule ex: 'COURANT' 'EPARGNE' ");

			}

		} catch (InvalidSwitchCaseException | InvalidHashPasswordException | InvalidAmountException
				| InvalidUserException | InvalidAccountException | InvalidPasswordException e) {

			Map<String, String> error = new HashMap<>();

			if (bindingResult.hasErrors()) {

				for (FieldError fd : bindingResult.getFieldErrors()) {
					error.put(fd.getField(), fd.getDefaultMessage());
				}

				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

			} else {
				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
			}

		}

	}

	@GetMapping(value = "/getAccount/{numer_compte}", produces = { "application/json" })
	public ResponseEntity<Object> chargerLeCompte(@PathVariable("numer_compte") Long numer_compte) {

		Map<String, String> error = new HashMap<>();

		try {

			Compte compte = compteContrat.findLeCompte(numer_compte);
			return new ResponseEntity<>(compte, HttpStatus.OK);

		} catch (Exception e) {
			error.put("Compte", "Compte Introuvable " + e);
			return new ResponseEntity<Object>(error, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	/*User n'existe pas
	 * @GetMapping(path = "/searchComptes", produces = { "application/json" })
	public Page<Compte> chercherComptes(@RequestParam(name = "mc1", defaultValue = "") String mc1,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		return compteContrat.findCompteParMotCle("%" + mc1 + "%", page, size);
	}*/

	@PostMapping(value = "/deleteAccount", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> supprimerLeCompte(@Valid @RequestBody Compte compte, BindingResult bindingResult) {

		try {

			return new ResponseEntity<>(
					compteContrat.disactivateAccount(compte.getNumeroCompte(), compte.getMotDePasse()), HttpStatus.OK);

		} catch (InvalidAccountException | InvalidPasswordException | InvalidConfirmationException
				| InvalidAdminDeletionException | InvalidHashPasswordException e) {

			Map<String, String> error = new HashMap<>();

			if (bindingResult.hasErrors()) {

				for (FieldError fd : bindingResult.getFieldErrors()) {
					error.put(fd.getField(), fd.getDefaultMessage());
				}

				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

			} else {
				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
			}

		}

	}
	@PutMapping(value = "/updateAccount", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateAccount(@Valid @RequestBody Compte updatedAccount) throws InvalidAccountException {
		if(CompteRepo.existsById(updatedAccount.getNumeroCompte())) {
			CompteRepo.save(updatedAccount);
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
		}
		throw new InvalidAccountException("Compte indisponible");
	}
	
	@GetMapping(value = "/accounts/{category}", produces = { "application/json" })
	public ResponseEntity<Object> getAccountsByCategory(@PathVariable("category") String category) {
		return new ResponseEntity<>(CompteRepo.findByCategorieCompte(CategorieCompte.valueOf(category)),HttpStatus.OK);

	}

}
=======
package tn.esprit.banque.controller.compte;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAdminDeletionException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidConfirmationException;
import tn.esprit.banque.exceptions.InvalidHashPasswordException;
import tn.esprit.banque.exceptions.InvalidPasswordException;
import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.exceptions.InvalidUserException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Compte.CategorieCompte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.OperationRepository;
import tn.esprit.banque.service.UtilisateurServiceImpl;
import tn.esprit.banque.service.compte.CanvasjsChartService;
import tn.esprit.banque.service.compte.CanvasjsChartServiceImpl.DatabaseConnectionException;
import tn.esprit.banque.service.compte.CompteContrat;
import tn.esprit.banque.CompteCourant;
import tn.esprit.banque.service.compte.CompteCreation;
import tn.esprit.banque.CompteEpargne;
import tn.esprit.banque.service.compte.CompteExcelExporter;

@Controller
public class CompteController {

	@Autowired
	private CompteContrat compteContrat;
	@Autowired
	private CompteRepository CompteRepo;
	@Autowired
	private UtilisateurServiceImpl user;
	
	 @Autowired 
	 private OperationRepository operation;
	
	 @Autowired
	 CompteCourant ct;
	 
	 @Autowired
	 CompteEpargne ep;


	@PostMapping(value = "/AccountCreation", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> creationCompte(@Valid @RequestBody CompteCreation cmpt, BindingResult bindingResult) {
		Compte compte = new Compte();
		try {

			if (!cmpt.getMotDePasse().equals(cmpt.getReMotDePasse())) {
				throw new InvalidPasswordException(
						"Veuillez re saisir le meme mot de passe dans le champ reMotDePasse Pour la confirmation ");
			}
			compte.setMotDePasse(cmpt.getMotDePasse());
			compte.setSoldeCompte(cmpt.getSoldeCompte());
			if (compte.getSoldeCompte().doubleValue() > 5000) {
				compte.setCategorieCompte(CategorieCompte.PLATINUM);
			} else if (compte.getSoldeCompte().doubleValue() > 2000) {
				compte.setCategorieCompte(CategorieCompte.GOLD);
			} else {
				compte.setCategorieCompte(CategorieCompte.SILVER);
			}
			switch (cmpt.getTypecompte()) {
			case "COURANT":
				return new ResponseEntity<>(
						ct.createAccount(compte, user.findUtilisateurById(cmpt.getUsername())),
						HttpStatus.OK);
			case "EPARGNE":
				return new ResponseEntity<>(
						ep.createAccount(compte, user.findUtilisateurById(cmpt.getUsername())),
						HttpStatus.OK);
			default:
				throw new InvalidAccountException(
						"Entrez un type du compte valide en majiscule ex: 'COURANT' 'EPARGNE' ");

			}

		} catch ( InvalidAmountException | InvalidUserException | InvalidAccountException
				| InvalidPasswordException e) {

			Map<String, String> error = new HashMap<>();

			if (bindingResult.hasErrors()) {

				for (FieldError fd : bindingResult.getFieldErrors()) {
					error.put(fd.getField(), fd.getDefaultMessage());
				}

				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

			} else {
				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
			}

		}

	}

	@GetMapping(value = "/getAccount/{numer_compte}", produces = { "application/json" })
	public ResponseEntity<Object> chargerLeCompte(@PathVariable("numer_compte") Long numer_compte) {

		Map<String, String> error = new HashMap<>();

		try {

			Compte compte = compteContrat.findLeCompte(numer_compte);
			return new ResponseEntity<>(compte, HttpStatus.OK);

		} catch (Exception e) {
			error.put("Compte", "Compte Introuvable " + e);
			return new ResponseEntity<Object>(error, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@PostMapping(value = "/deleteAccount", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> supprimerLeCompte(@Valid @RequestBody Compte compte, BindingResult bindingResult) {

		try {

			return new ResponseEntity<>(
					compteContrat.disactivateAccount(compte.getNumeroCompte(), compte.getMotDePasse()), HttpStatus.OK);

		} catch (InvalidAccountException | InvalidPasswordException | InvalidConfirmationException
				| InvalidAdminDeletionException | InvalidHashPasswordException e) {

			Map<String, String> error = new HashMap<>();

			if (bindingResult.hasErrors()) {

				for (FieldError fd : bindingResult.getFieldErrors()) {
					error.put(fd.getField(), fd.getDefaultMessage());
				}

				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

			} else {
				error.put("message", e.toString());
				return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
			}

		}

	}

	@PutMapping(value = "/updateAccount", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateAccount(@Valid @RequestBody Compte updatedAccount)
			throws InvalidAccountException {
		if (CompteRepo.existsById(updatedAccount.getNumeroCompte())) {
			CompteRepo.save(updatedAccount);
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
		}
		throw new InvalidAccountException("Compte indisponible");
	}

	@GetMapping(value = "/accounts/{category}", produces = { "application/json" })
	public ResponseEntity<Object> getAccountsByCategory(@PathVariable("category") String category) {
		return new ResponseEntity<>(CompteRepo.findByCategorieCompte(CategorieCompte.valueOf(category)), HttpStatus.OK);

	}

	@GetMapping("/operations/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=operations_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Operation> listUsers = operation.findAll();

		CompteExcelExporter excelExporter = new CompteExcelExporter(listUsers);

		excelExporter.export(response);
	}

	
		@Autowired
		private CanvasjsChartService canvasjsChartService;

		@RequestMapping(path = "/chart",method = RequestMethod.GET)
		public String springMVC(ModelMap modelMap) {
			List<List<Operation>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();
			modelMap.put("dataPointsList", canvasjsDataList);
			return "chart";
		}

		@ExceptionHandler({ DatabaseConnectionException.class })
		public ModelAndView getSuperheroesUnavailable(DatabaseConnectionException ex) {
			return new ModelAndView("chart", "error", ex.getMessage());
		}
	

}
>>>>>>> master
