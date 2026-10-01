package com.Blog.Blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtAuthResponse {

    private String token;

    private  UserDto user;
}
