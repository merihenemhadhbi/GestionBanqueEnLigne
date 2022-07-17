package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Post;
import tn.esprit.banque.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository; 
	@Override
	public Post addPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post updatePost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deletePost(Long id) {
postRepository.deleteById(id);	
	}

	@Override
	public List<Post> findAllPost() {
		return (List<Post>) postRepository.findAll();
	}

	@Override
	public Post findPostById(Long id) {
		// TODO Auto-generated method stub
		return postRepository.findById(id).get();
	}

}
