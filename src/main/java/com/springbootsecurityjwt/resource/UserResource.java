package com.springbootsecurityjwt.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    @GetMapping("/register")
    public String registerUser(){
        System.out.println("-------in register-----------------");
        return "User successfully registered..!!";
    }
}
