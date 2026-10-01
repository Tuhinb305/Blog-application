package com.Blog.Blog.repository;

import com.Blog.Blog.entity.Category;
import com.Blog.Blog.entity.Post;
import com.Blog.Blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findAllByUser(User user);

    List<Post> findAllByCategory(Category category);

    @Query("SELECT p FROM Post p WHERE p.title LIKE :key")
    List<Post> searchByTitle(@Param("key") String key);
}