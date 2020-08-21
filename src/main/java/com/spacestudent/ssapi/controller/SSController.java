package com.spacestudent.ssapi.controller;

import com.google.gson.Gson;
import com.spacestudent.ssapi.model.User;
import com.spacestudent.ssapi.payload.BasicRestResponse;
import com.spacestudent.ssapi.service.impl.UserCreationAnAuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/ss/api")
public class SSController {

    @Autowired
    UserCreationAnAuthServiceImpl userCreationAnAuthService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SSController.class);

    @GetMapping("/auth/signIn")
    public BasicRestResponse signIn(@RequestParam String username, @RequestParam String password) {
        LOGGER.info("Login with===> Username {} and password {}", username, password);

        return userCreationAnAuthService.signUp(username, password);
    }

    @PostMapping("/auth/signUp")
    public BasicRestResponse signUp(@RequestBody User user){
        LOGGER.info("Creating new user, params <===============> \n {}",new Gson().toJson(user));

        return userCreationAnAuthService.createUser(user);
    }

}
