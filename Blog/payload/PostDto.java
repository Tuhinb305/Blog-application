package com.Blog.Blog.payload;

import com.Blog.Blog.entity.Comment;
import com.Blog.Blog.entity.Post;
import com.Blog.Blog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;

    private String content;

    private String image;

   private String imageName;

   private Date addedDate;

   private User user;

   private Integer postId;

   private Set<CommentDto> comments=new HashSet<>();


}
