package tn.esprit.banque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.RetraitATM;

public interface RetraitATMRepository extends JpaRepository<RetraitATM, Long> {
	List<RetraitATM> findByCarte(Carte Carte);

}