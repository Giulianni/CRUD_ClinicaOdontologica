package com.dh.clinica.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);


    @GetMapping("/")
    public String home() {
        return "Bienvenido!";
    }

    @GetMapping("/user")
    public String user() {
        return "Welcome User!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "BENVENUTO admin!";
    }
}
