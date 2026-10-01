package com.Blog.Blog.services.impl;

import com.Blog.Blog.config.AppConstant;
import com.Blog.Blog.entity.Role;
import com.Blog.Blog.entity.User;
import com.Blog.Blog.exception.ResourceNotFoundException;
import com.Blog.Blog.payload.UserDto;
import com.Blog.Blog.repository.RoleRepo;
import com.Blog.Blog.repository.UserRepo;
import com.Blog.Blog.services.UserService;
//import org.modelmapper.ModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//এই code-এ Controller → DTO → Service → Entity → Repository → Database এই flow follow করা হচ্ছে।
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;


    @Override
    public UserDto registerNewUser(UserDto userDto) {


        // Check duplicate email
        if(this.userRepo.findByEmail(userDto.getEmail()).isPresent()) {

            throw new RuntimeException("Email already registered");

        }


        User user = this.modelMapper.map(userDto, User.class);


        // Encode password
        user.setPassword(
                this.passwordEncoder.encode(user.getPassword())
        );


        Role role = this.roleRepo
                .findById(AppConstant.NORMAL_USER)
                .get();


        user.getRoles().add(role);


        User newUser = this.userRepo.save(user);


        return this.modelMapper.map(newUser, UserDto.class);
    }
    @Override
    public User getUserByEmail(String email) {

        return this.userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );
    }

    @Override
    public UserDto createUser(UserDto userDto) {

       User user= this.dtoToUser(userDto);
       User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
      user.setName(userDto.getName());
      user.setEmail(userDto.getEmail());
      user.setPassword(userDto.getPassword());
      user.setAbout(userDto.getAbout());
      User updateUser=this.userRepo.save(user);
      UserDto userDto1= this.userToDto(updateUser);
      return userDto1;


    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
                "User","Id",userId));


        return this.userToDto(user);

    }

    @Override
    public List<UserDto> getAllUser() {

        List<User>users=this.userRepo.findAll();
      List<UserDto>userDtos= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());


        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

       User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException
                ("User","Id",userId));
       this.userRepo.delete(user);


    }



    private User dtoToUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto,User.class);


        /*user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());*/

        return user;
    }

    public UserDto userToDto(User user) {

        UserDto userDto = this.modelMapper.map(user,UserDto.class);

        return userDto;


    }
}
