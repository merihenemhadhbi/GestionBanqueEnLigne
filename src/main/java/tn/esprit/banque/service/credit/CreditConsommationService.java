package tn.esprit.banque.service.credit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidMensualiteException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.MailService;
import tn.esprit.banque.service.compte.CompteContrat;

@Service
public class CreditConsommationService extends CreditAbstractionService {
	@Autowired
	private MailService mailService;
	
	public BigDecimal getTMM() {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.bct.gov.tn/bct/siteprod/tableau_statistique_a.jsp?params=PL203105&la=AR")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements tableRows = doc.select(".bct-table-fixed table tr");
		BigDecimal lastValue = new BigDecimal(0);
		for (Element tableRow : tableRows) {
			String rowData = tableRow.text();
			if (rowData.split(" ").length == 7) {
				lastValue = new BigDecimal(rowData.split(" ")[6]);
			}
		}
		return lastValue;

	}

	private CreditRepository creditRepository;
	@Autowired 
	private CompteRepository compteRepository;
	private CompteContrat compteContrat;

	@Autowired
	public void setCreditRepository(CreditRepository creditRepository) {
		this.creditRepository = creditRepository;
	}

	@Autowired
	public void setCompteContrat(CompteContrat compteContrat) {
		this.compteContrat = compteContrat;
	}

	public CreditConsommationService() {
		credit = Credits.TypeCredit.CONSOMMATION;
	}

	@Transactional
	@Override
	public Credits createCredit(Credits credits, Compte compte)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException {
		// TODO Auto-generated method stub
		credits.setCompteCredit(compte);
		credits.setDateCredit(new Date());
		credits.setTypeCredit(credit);

		if (!compte.isEtatCompte()) {
			throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
		} else if (credits.getMontantCredit().longValue() <= 1000 || credits.getMontantCredit().longValue() > 5000) {
			throw new InvalidAmountException("Montant specifié est supérieur à 1000 et ne dépasse pas 5000");
		} else if (credits.getNombreMensualitesCredit() > 36) {
			throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas  3 ans ");
		} else {

			BigDecimal TMM = getTMM();

			if (credits.getNombreMensualitesCredit() >= 12 && credits.getNombreMensualitesCredit() <= 36) {
				BigDecimal interet = TMM.add(new BigDecimal(2)).divide(new BigDecimal(100));
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				Long MontantMax = compte.getSoldeCompte().longValue() * 40 / 100;
				if (MontantMax >= MontantApayerMensuelle) {
					credits.setApprouver(true);
				} else {
					credits.setApprouver(false);
				}
			}
			return creditRepository.save(credits);

		}

	}
	@Override
	public Credits affectercredit(Credits credit, Compte compte) {
		double montantMaxApayer = (credit.getCompteCredit().getSoldeCompte().doubleValue() * 40) / 100;

		if (credit.getMensualite() <= montantMaxApayer) {
			int nombreEnfant;
			Double restSolde = credit.getCompteCredit().getSoldeCompte().doubleValue() - credit.getMensualite();
			Double salairesuff = (Double.valueOf(((Physique) credit.getCompteCredit().getUtilisateur()).getNb_enfant())
					* 150) + 400;

			if (restSolde >= salairesuff) {
				credit.setApprouver(true);
				credit.setCompteCredit(compte);
			mailService.envoyer(compte.getUtilisateur().getEmail(), "Acceptation de credit", "Bonjour msr , votre credit est accepter !");
			}

			else {
				credit.setApprouver(false);
			}
		}
		return creditRepository.save(credit);
	}



	@Override
	public Credits Createothercredit(Credits nv_credit, Long idCompte) {

		Compte compte = compteRepository.findById(idCompte).get();
		Credits Ac_credit = creditRepository.findById(idCompte).get();

		if (Ac_credit.getApprouver().equals(true)) {
			double montantpaye, rest, newmontant;
			montantpaye = Ac_credit.getMensualite() * nv_credit.getNombreMensualitesCredit();
			rest = Ac_credit.getMontantCredit() - montantpaye;
			newmontant = Ac_credit.getMontantCredit() - rest;
			if (newmontant > 0) {
				nv_credit.setApprouver(true);
				nv_credit.setCompteCredit(compte);
				nv_credit.setMontantCredit(newmontant);

				BigDecimal TMM = getTMM();

				if (nv_credit.getNombreMensualitesCredit() >= 12 && nv_credit.getNombreMensualitesCredit() <= 36) {
					BigDecimal interet = TMM.add(new BigDecimal(2)).divide(new BigDecimal(100));
					Double MontantSansInteret = nv_credit.getMontantCredit();
					Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet.doubleValue());
					Double MontantApayerMensuelle = MontantAvecInteret / nv_credit.getNombreMensualitesCredit();
					nv_credit.setMensualite(MontantApayerMensuelle);
					Long MontantMax = compte.getSoldeCompte().longValue() * 40 / 100;
					if (MontantMax >= MontantApayerMensuelle) {
						nv_credit.setApprouver(true);
					} else {
						nv_credit.setApprouver(false);
					}
				}

			}

		}				return creditRepository.save(nv_credit);

	}


}