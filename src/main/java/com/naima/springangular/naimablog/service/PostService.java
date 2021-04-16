package com.naima.springangular.naimablog.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naima.springangular.naimablog.dto.PostDto;
import com.naima.springangular.naimablog.exception.PostNotFoundException;
import com.naima.springangular.naimablog.model.Post;
import com.naima.springangular.naimablog.repository.PostRepository; 
import static java.util.stream.Collectors.toList;

@Service
public class PostService {
	
	@Autowired
	private AuthService authService;
	@Autowired PostRepository postRepository;
	
	 @Transactional
	    public List<PostDto> showAllPosts() {
	        List<Post> posts = postRepository.findAll();
	        return posts.stream().map(this::mapFromPostToDto).collect(toList());
	    }
	  @Transactional
	    public void createPost(PostDto postDto) {
	        Post post = mapFromDtoToPost(postDto);
	        postRepository.save(post);
	    }

	    @Transactional
	    public PostDto readSinglePost(Long id) {
	        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
	        return mapFromPostToDto(post);
	    }
	
/*	public void createPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User username = authService.getCurrentUser().orElseThrow(()-> new IllegalArgumentException("No user logged in"));
		post.setUsername(username.getUsername());
		post.setCreatedOn(Instant.now());
		postRepository.save(post);
	}*/
	
	 private PostDto mapFromPostToDto(Post post) {
	        PostDto postDto = new PostDto();
	        postDto.setId(post.getId());
	        postDto.setTitle(post.getTitle());
	        postDto.setContent(post.getContent());
	        postDto.setUsername(post.getUsername());
	        return postDto;
	    }

	    private Post mapFromDtoToPost(PostDto postDto) {
	        Post post = new Post();
	        post.setTitle(postDto.getTitle());
	        post.setContent(postDto.getContent());
	        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
	        post.setCreatedOn(Instant.now());
	        post.setUsername(loggedInUser.getUsername());
	        post.setUpdatedOn(Instant.now());
	        return post;
	    }
}
