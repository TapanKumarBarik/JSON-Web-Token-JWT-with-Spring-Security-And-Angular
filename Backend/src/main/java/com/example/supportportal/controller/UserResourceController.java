package com.example.supportportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserResourceController {

    @GetMapping()
    public String getUser(){
        return "yehh it is step 1";
    }
}
