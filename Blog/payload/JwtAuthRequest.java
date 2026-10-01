package com.Blog.Blog.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    public String password;
}
