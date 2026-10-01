package com.Blog.Blog.entity;

//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    private  String content;

    @ManyToOne
    private Post post;

    

}
