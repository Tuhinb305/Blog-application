package com.Blog.Blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter

@NoArgsConstructor
public class PostResponse {
    private List<PostDto>content;

    private  int pageNumber;
    private  int pageSize;
    private  long totalElement;
    private  int totalPages;

    private  boolean lastPage;




}
