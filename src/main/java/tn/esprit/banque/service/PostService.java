package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.Post;

public interface PostService {
	Post addPost(Post post);
	Post updatePost(Post post);
	void deletePost(Long id);
	List<Post> findAllPost();
	Post findPostById(Long id);
}
