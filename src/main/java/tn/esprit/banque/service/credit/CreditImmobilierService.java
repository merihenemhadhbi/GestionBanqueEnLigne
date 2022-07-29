package tn.esprit.banque.service.credit;

import java.io.IOException;
import java.util.Date;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidMensualiteException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.compte.CompteContrat;

@Service
public class CreditImmobilierService extends CreditAbstractionService {
	private static final Logger LOG = LoggerFactory.getLogger(CreditImmobilierService.class);

	public Double getTMM() {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.bct.gov.tn/bct/siteprod/tableau_statistique_a.jsp?params=PL203105&la=AR")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements tableRows = doc.select(".bct-table-fixed table tr");
		Double lastValue = 0d;
		for (Element tableRow : tableRows) {
			String rowData = tableRow.text();
			if (rowData.split(" ").length == 7) {
				lastValue = Double.parseDouble(rowData.split(" ")[6]);
			}
		}
		return lastValue;

	}
	@Autowired
	private CreditRepository creditRepository;
	@Autowired
	CompteRepository compteRepository;
	
	private CompteContrat compteContrat;

	@Autowired
	public void setCreditRepository(CreditRepository creditRepository) {
		this.creditRepository = creditRepository;
	}

	@Autowired
	public void setCompteContrat(CompteContrat compteContrat) {
		this.compteContrat = compteContrat;
	}

	public CreditImmobilierService() {
		credit = Credits.TypeCredit.IMMOBILIER;
	}

	@Transactional
	@Override
	public Credits createCredit(Credits credits, Compte compte)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException {
		credits.setCompteCredit(compte);
		credits.setDateCredit(new Date());
		credits.setTypeCredit(credit);

		if (!compte.isEtatCompte()) {
			throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
		} else if (credits.getMontantCredit().longValue() <= 10000) {
			throw new InvalidAmountException("Montant specifié est supérieur à 10000");
		} else if (credits.getNombreMensualitesCredit() > 240) {
			throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas  20 ans ");
		} else {

			Double TMM = getTMM();
			if (credits.getNombreMensualitesCredit() <= 84) {
				Double interet = (TMM+2)/100;
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);
				System.out.print(interet);
			

			} else if (credits.getNombreMensualitesCredit() > 84 && credits.getNombreMensualitesCredit() <= 180) {

				Double interet =  ((TMM + 2.25) / 100);
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);

			

			} else if (credits.getNombreMensualitesCredit() > 180 && credits.getNombreMensualitesCredit() <= 240) {

				Double interet =  ((TMM + 2.5) / 100);
				Double MontantSansInteret = credits.getMontantCredit();
				Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
				Double MontantApayerMensuelle = MontantAvecInteret / credits.getNombreMensualitesCredit();
				credits.setMensualite(MontantApayerMensuelle);
				credits.setInteret(interet);

			}

		}
		return creditRepository.save(credits);
	}
	
	@Override
	public Credits affectercredit(Long idCredit) {
		Credits credit = creditRepository.findById(idCredit).get();

		double montantMaxApayer  = (credit.getCompteCredit().getSoldeCompte().doubleValue() * 40) / 100; 
		
		if (credit.getMensualite() <= montantMaxApayer) {
			int nombreEnfant;
	       Double restSolde = credit.getCompteCredit().getSoldeCompte().doubleValue() - credit.getMensualite();
			//Double salairesuff = (credit.getCompteCredit().getUtilisateur() * 150) + 400;

		//	if (restSolde >= salairesuff) {
				credit.setApprouver(true);
				return creditRepository.save(credit);
			}

			else
				credit.setApprouver(false);
			return creditRepository.save(credit);

		}
  
	
	@Override
	public Credits Createothercredit(Credits nv_credit, Long idCompte) {

		Compte compte =compteRepository.findById(idCompte).get();
		Credits Ac_credit = creditRepository.findById(idCompte).get();

		if (Ac_credit.getApprouver().equals(true)) {
			double montantpaye, rest, newmontant;
			montantpaye = Ac_credit.getMensualite() * nv_credit.getNombreMensualitesCredit();
			rest = Ac_credit.getMontantCredit() - montantpaye;
			newmontant = Ac_credit .getMontantCredit() - rest;
			if (newmontant > 0) {
				nv_credit.setApprouver(true);
				nv_credit.setCompteCredit(compte);
				nv_credit.setMontantCredit(newmontant);

				Double TMM = getTMM();
				if (nv_credit.getNombreMensualitesCredit() <= 84) {
					Double interet = (TMM+2)/100;
					Double MontantSansInteret = nv_credit.getMontantCredit();
					Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
					Double MontantApayerMensuelle = MontantAvecInteret / nv_credit.getNombreMensualitesCredit();
					nv_credit.setMensualite(MontantApayerMensuelle);
					nv_credit.setInteret(interet);
					System.out.print(interet);
				

				} else if (nv_credit.getNombreMensualitesCredit() > 84 && nv_credit.getNombreMensualitesCredit() <= 180) {

					Double interet =  ((TMM + 2.25) / 100);
					Double MontantSansInteret = nv_credit.getMontantCredit();
					Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
					Double MontantApayerMensuelle = MontantAvecInteret / nv_credit.getNombreMensualitesCredit();
					nv_credit.setMensualite(MontantApayerMensuelle);
					nv_credit.setInteret(interet);

				

				} else if (nv_credit.getNombreMensualitesCredit() > 180 && nv_credit.getNombreMensualitesCredit() <= 240) {

					Double interet =  ((TMM + 2.5) / 100);
					Double MontantSansInteret = nv_credit.getMontantCredit();
					Double MontantAvecInteret = MontantSansInteret + (MontantSansInteret + interet);
					Double MontantApayerMensuelle = MontantAvecInteret / nv_credit.getNombreMensualitesCredit();
					nv_credit.setMensualite(MontantApayerMensuelle);
					nv_credit.setInteret(interet);

				}

			}

}
		return creditRepository.save(nv_credit);

	}
	}
