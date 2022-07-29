package tn.esprit.banque.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.banque.model.Commentaire;
import tn.esprit.banque.model.Post;
import tn.esprit.banque.service.CommentaireService;
import tn.esprit.banque.service.PostService;

@Controller
public class CommentaireController {
	@Autowired
	private CommentaireService commentaireService;
	@Autowired
	private PostService postService;
	@PostMapping(value = "/addCommentaire/{idPost}")
	public ResponseEntity addComment(@RequestBody Commentaire commentaire, @PathVariable Long idPost) {
		Commentaire commentairePostSave = null;
		Post post = null;
		try {
			post = postService.findPostById(idPost);
			commentaire.setPost(post);
			commentairePostSave = commentaireService.addCommentaire(commentaire);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body("Post not found");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(commentairePostSave);
	}
	@PutMapping(value = "/updateCommentaire")
	public ResponseEntity updateCommentaire(@RequestBody Commentaire commentaire) {
		Commentaire commentairePostSave = null;
		Post post = null;
		try {
			post = commentaireService.findCommentaireById(commentaire.getId_commentaire()).getPost();
			commentaire.setPost(post);
			commentairePostSave = commentaireService.updateCommentaire(commentaire);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(commentairePostSave);
	}

	@DeleteMapping(value = "/deleteCommentaire/{id}")
	public ResponseEntity deleteCommentaire(@PathVariable Long id) {
		try {
			commentaireService.deleteCommentaire(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Comment deleted");
	}

	@GetMapping(value = "/findAllCommentairesByPost/{idPost}")
	public ResponseEntity findAllComments(@PathVariable Long idPost) {
		List<Commentaire> commentList = new ArrayList<>();
		Post post = null;
		try {
			post = postService.findPostById(idPost);
			commentList = post.getCommentaire();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(commentList);
	}

	@GetMapping(value = "/findCommentaire/{id}")
	public ResponseEntity findComment(@PathVariable Long id) {
		Commentaire commentaire = null;
		try {
			commentaire = commentaireService.findCommentaireById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(commentaire);
	}
}
