package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Title must have minimum 4 chars")
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 4, message = "Title must have minimum 4 chars")
    private String body;
}
