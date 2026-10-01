package com.Blog.Blog.services.impl;

import com.Blog.Blog.entity.Comment;
import com.Blog.Blog.entity.Post;
import com.Blog.Blog.exception.ResourceNotFoundException;
import com.Blog.Blog.payload.CommentDto;
import com.Blog.Blog.repository.CommentRepo;
import com.Blog.Blog.repository.PostRepo;
import com.Blog.Blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post", "postId", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment", "commentId", commentId));

        this.commentRepo.delete(comment);
    }
}