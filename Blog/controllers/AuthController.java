package com.Blog.Blog.controllers;
import com.Blog.Blog.entity.User;
import com.Blog.Blog.exception.ApiException;
import com.Blog.Blog.payload.JwtAuthRequest;
import com.Blog.Blog.payload.JwtAuthResponse;
import com.Blog.Blog.payload.UserDto;
import com.Blog.Blog.security.JwtTokenHelper;
import com.Blog.Blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    @Autowired
    public JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request) throws Exception {


        this.authenticate(
                request.getUsername(),
                request.getPassword()
        );


        UserDetails userDetails =
                this.userDetailsService
                        .loadUserByUsername(request.getUsername());


        String token =
                this.jwtTokenHelper.generateToken(userDetails);


        User user =
                this.userService.getUserByEmail(
                        request.getUsername()
                );


        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(token);

        response.setUser(
                this.mapper.map(user, UserDto.class)
        );


        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );

    }

    private void authenticate(String username, String password) throws Exception {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {

            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("wrong user name password  details");
            throw new ApiException("invaliad username or password");
        }

    }

    /// register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(
            @Valid @RequestBody UserDto userDto
    ) {

        UserDto registerUser = this.userService.registerNewUser(userDto);

        return new ResponseEntity<UserDto>(
                registerUser,
                HttpStatus.CREATED
        );

    }
}