package com.Blog.Blog.services;

import com.Blog.Blog.entity.User;
import com.Blog.Blog.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);

    UserDto getUserById(Integer userId);
    List<UserDto>getAllUser();

    void deleteUser(Integer userId);

    User getUserByEmail(String email);


}
