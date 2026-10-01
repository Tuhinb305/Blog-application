package com.Blog.Blog.services;

import com.Blog.Blog.entity.Post;
import com.Blog.Blog.payload.PostDto;
import com.Blog.Blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    //create post
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update post

    PostDto updatePost(PostDto postDto,Integer postId);

    //delete

    void deletePost(Integer postId);

    //get all post

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);


    //get a single post

    PostDto getPostById(Integer postId);

    //get post by category

    List<PostDto>getPostByCategory(Integer categoryId);

    //get all post by user

    List<PostDto>getPostByUser(Integer userId);
    //search post by id
    List<PostDto>searchPost(String keyword);





}
