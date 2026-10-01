package com.Blog.Blog.services;

import com.Blog.Blog.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);

    void  deleteComment(Integer commentId);

}
