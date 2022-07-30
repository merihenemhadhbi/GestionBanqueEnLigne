package tn.esprit.banque.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.banque.model.Post;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.PostRepository;
import tn.esprit.banque.service.PostService;


@RestController
public class PostController {
@Autowired
 private PostService postService; 
@PostMapping("/addPost")
public ResponseEntity createPost(@RequestBody Post post) {
	Post postSave = null;
	try {
		post.setUtilisateur((Utilisateur)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		postSave = postService.addPost(post);
	} catch (Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	return ResponseEntity.status(HttpStatus.CREATED).body(postSave);
}
@PutMapping(value = "/updatePost")
public ResponseEntity updateTopic(@RequestBody Post post) {
	Post postSave = null;
	try {
		postSave = postService.updatePost(post);
	} catch (Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	return ResponseEntity.status(HttpStatus.OK).body(postSave);
}


@DeleteMapping(value = "/deletePost/{id}")
public ResponseEntity deletePost(@PathVariable Long id) {
	try {
		postService.deletePost(id);
	} catch (Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	return ResponseEntity.status(HttpStatus.OK).body("Post deleted");
}
@GetMapping(value = "/findAllPost")
public ResponseEntity findAllPost() {
	List<Post> postList = new ArrayList<>();
	try {
		postList = postService.findAllPost();
	} catch (Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	return ResponseEntity.status(HttpStatus.OK).body(postList);
}

@GetMapping(value = "/findPost/{id}")
public ResponseEntity findPost(@PathVariable Long id) {
	Post post = null;
	try {
		post = postService.findPostById(id);
	} catch (Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest().body("Post not found");
	}
	return ResponseEntity.status(HttpStatus.OK).body(post);
}
}
