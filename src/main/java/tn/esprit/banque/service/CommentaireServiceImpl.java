package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Commentaire;
import tn.esprit.banque.repository.CommentaireRepository;
@Service
public class CommentaireServiceImpl implements CommentaireService {
	@Autowired
	private CommentaireRepository commentaireRepository;

	@Override
	public Commentaire addCommentaire(Commentaire commentaire) {
		return commentaireRepository.save(commentaire);

	}

	@Override
	public Commentaire updateCommentaire(Commentaire commentaire) {
		return commentaireRepository.save(commentaire);

	}

	@Override
	public void deleteCommentaire(Long id) {
commentaireRepository.deleteById(id);		
	}

	@Override
	public List<Commentaire> findAllCommentaires() {
		return  (List<Commentaire>)commentaireRepository.findAll();
	}

	@Override
	public Commentaire findCommentaireById(Long id) {
		return commentaireRepository.findById(id).get();
	}

}
