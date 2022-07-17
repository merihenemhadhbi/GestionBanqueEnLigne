package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.Commentaire;

public interface CommentaireService {
	Commentaire addCommentaire(Commentaire commentaire);

	Commentaire updateCommentaire(Commentaire commentaire);

	void deleteCommentaire(Long id);

	List<Commentaire> findAllCommentaires();

	Commentaire findCommentaireById(Long id);
}
