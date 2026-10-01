package com.Blog.Blog.controllers;

import com.Blog.Blog.payload.ApiResponse;
import com.Blog.Blog.payload.CommentDto;
import com.Blog.Blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create Comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId) {

        CommentDto createdComment = this.commentService.createComment(commentDto, postId);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Delete Comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer commentId) {

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<>(
                new ApiResponse("Comment deleted successfully", true),
                HttpStatus.OK);
    }
}