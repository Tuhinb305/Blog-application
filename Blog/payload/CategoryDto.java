package com.Blog.Blog.payload;


//import jakarta.persistence.Column;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message = "minimum size 4")
    private String categoryTitle;
    @NotBlank
    @Size(min =10,message = "minimum size of 10")
    private  String categoryDescription;


}
