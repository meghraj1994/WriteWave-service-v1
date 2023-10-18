package com.springboot.blog.payload;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class PostDto {
    private long id;

    //requirement : title should not be null or empty and title should have at least 2 chars
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    //requirement : post description should not be null or empty, and it should have at least 10 chars
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    //requirement: content should not be null or empty
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
