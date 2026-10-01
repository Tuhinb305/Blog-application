package com.Blog.Blog.services.impl;

import com.Blog.Blog.entity.Category;
import com.Blog.Blog.entity.Post;
import com.Blog.Blog.entity.User;
import com.Blog.Blog.exception.ResourceNotFoundException;
import com.Blog.Blog.payload.PostDto;
import com.Blog.Blog.payload.PostResponse;
import com.Blog.Blog.repository.CategoryRepo;
import com.Blog.Blog.repository.PostRepo;
import com.Blog.Blog.repository.UserRepo;
import com.Blog.Blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User", "User id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categortId", categoryId));


        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());

        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);


    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAddedDate(postDto.getAddedDate());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "postId", postId));
        this.postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if (sortDir.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        // Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();

        List<PostDto> postDtos = allPost.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
PostResponse postResponse=new PostResponse();
postResponse.setPageNumber(pagePost.getNumber());
postResponse.setContent(postDtos);
postResponse.setPageSize(pagePost.getSize());
postResponse.setTotalElement(pagePost.getTotalElements());
postResponse.setTotalPages(pagePost.getTotalPages());
postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "postId", postId));


        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "CategoryId", categoryId));

        List<Post> posts = this.postRepo.findAllByCategory(cat);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "user", userId));
        List<Post> posts = this.postRepo.findAllByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;

    }
}




