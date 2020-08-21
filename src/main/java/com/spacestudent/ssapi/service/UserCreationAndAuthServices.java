package com.spacestudent.ssapi.service;

import com.spacestudent.ssapi.model.User;
import com.spacestudent.ssapi.payload.BasicRestResponse;

public interface UserCreationAndAuthServices {

    BasicRestResponse createUser(User newUser);

    BasicRestResponse signUp(String login, String password);



}
