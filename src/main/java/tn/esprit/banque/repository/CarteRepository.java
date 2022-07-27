package tn.esprit.banque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Compte.CategorieCompte;

public interface CarteRepository extends JpaRepository<Carte, Long> {
	List<Carte> findByCompte(Compte compte);

}
