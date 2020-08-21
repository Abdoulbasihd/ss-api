package com.spacestudent.ssapi.payload.Authentication;

import com.spacestudent.ssapi.model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class SignUpResponse implements Serializable {
    private String token;
    private String username;
    private User user;
}
