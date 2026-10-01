package com.Blog.Blog.repository;

import com.Blog.Blog.entity.Category;
import com.Blog.Blog.entity.Post;
import com.Blog.Blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Integer> {





}
