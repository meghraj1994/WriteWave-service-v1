package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SignUpDto {

    private String name;
    private String username;

    @Email
    private String email;
    private String password;
}
