package com.Blog.Blog.payload;

import com.Blog.Blog.entity.Post;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
public class CommentDto {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    private  String content;





}
