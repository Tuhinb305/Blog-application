package com.Blog.Blog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {


    private int id;


    @NotEmpty(message = "Name is required")
    @Size(
            min = 4,
            message = "Name must be minimum 4 characters"
    )
    private String name;



    @Column(unique = true)
    @NotEmpty(message = "Email is required")
    @Email(message = "Email address is not valid")
    private String email;



    @JsonIgnore
    @NotEmpty(message = "Password is required")
    @Size(
            min = 3,
            max = 10,
            message = "Password must be minimum 3 and maximum 10 characters"
    )
    private String password;



    @NotEmpty(message = "About is required")
    private String about;



    private Set<RoleDto> roles = new HashSet<>();


    @JsonIgnore
    public String getPassword() {
        return password;
    }

}