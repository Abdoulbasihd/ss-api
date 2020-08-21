package com.spacestudent.ssapi.service.impl;

import com.google.gson.Gson;
import com.spacestudent.ssapi.model.Group;
import com.spacestudent.ssapi.model.User;
import com.spacestudent.ssapi.payload.Authentication.SignUpResponse;
import com.spacestudent.ssapi.payload.BasicRestResponse;
import com.spacestudent.ssapi.payload.rest.ResponseCode;
import com.spacestudent.ssapi.repository.GroupRepository;
import com.spacestudent.ssapi.repository.UserRepository;
import com.spacestudent.ssapi.security.JwtTokenProvider;
import com.spacestudent.ssapi.service.UserCreationAndAuthServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCreationAnAuthServiceImpl implements UserCreationAndAuthServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreationAnAuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * To create new account
     * @param newUser
     * @return
     */
    @Override
    public BasicRestResponse createUser(User newUser) {
        Optional<Group> optGroup = groupRepository.findByGroupName(newUser.getExaminationLevel());

        BasicRestResponse restResponse = userVerifier(newUser);

        if(restResponse!=null)
            return restResponse;

        BasicRestResponse response = new BasicRestResponse();

        try {
            newUser.setGroup(optGroup.get());
            newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));

            User user = userRepository.save(newUser);

            response.setData(user);
            response.setMessage(ResponseCode.TRANSACTION_SUCCESSFUL.getDescription());
            response.setStatus(ResponseCode.TRANSACTION_SUCCESSFUL.getCode());

        }catch (Exception e){
            response.setStatus(ResponseCode.ERROR_WHILE_SAVING_DATA.getCode());
            response.setMessage(ResponseCode.ERROR_WHILE_SAVING_DATA.getDescription());
        }


        return response;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public BasicRestResponse signUp(String username, String password) {
        BasicRestResponse  basicRestResponse = new BasicRestResponse();

        try {

            Optional<User> optionalUser = userRepository.findByUsername(username);

            LOGGER.info("User  {}", new Gson().toJson(optionalUser.get()));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            LOGGER.info("After Authenticate  ");
            //String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
            String token = jwtTokenProvider.createToken(username, optionalUser.get().getRoles());
            LOGGER.info("Token  {}", token);

            SignUpResponse signUpResponse = new SignUpResponse();
            signUpResponse.setToken(token);
            signUpResponse.setUsername(optionalUser.get().getUsername());
            signUpResponse.setUser(optionalUser.get());

            basicRestResponse.setData(signUpResponse);
            basicRestResponse.setMessage(ResponseCode.TRANSACTION_SUCCESSFUL.getDescription());
            basicRestResponse.setStatus(ResponseCode.TRANSACTION_SUCCESSFUL.getCode());


        }catch (Exception exception){
            //throw new BadCredentialsException("Invalid username/password supplied");
            LOGGER.info("Authentication exception  {}", exception.getMessage());
            exception.printStackTrace();

            basicRestResponse.setData(null);
            basicRestResponse.setMessage(ResponseCode.INVALID_USERNAME_OR_PASSWORD.getDescription()+" ==> ");
            basicRestResponse.setStatus(ResponseCode.INVALID_USERNAME_OR_PASSWORD.getCode());

        }

        LOGGER.info("Sign Up response ::   {}", new Gson().toJson(basicRestResponse));
        return basicRestResponse;

    }

    /**
     *
     * @param userToVerify
     * @return
     *  Return null if user is verified (everything is ok).
     *  If user is not verified, return RestResponse with the error code (status) and the message.
     *
     */
    private BasicRestResponse userVerifier(User userToVerify){
        Optional<Group> optGroup = groupRepository.findByGroupName(userToVerify.getExaminationLevel());
        Optional<User> optionalUser = userRepository.findByUsername(userToVerify.getUsername());

        if (!optGroup.isPresent())
            return new BasicRestResponse(ResponseCode.SELECTED_GROUP_NOT_EXIST.getCode(), ResponseCode.SELECTED_GROUP_NOT_EXIST.getDescription(), null);
        else if (optionalUser.isPresent())
            return new BasicRestResponse(ResponseCode.USERNAME_ALREADY_USED.getCode(), ResponseCode.USERNAME_ALREADY_USED.getDescription(), null);
        //else if (userToVerify==null)
           // return new BasicRestResponse(ResponseCode.SEND_DATA_NULL_FOR_ACCOUNT_CREATION.getCode(), ResponseCode.SEND_DATA_NULL_FOR_ACCOUNT_CREATION.getDescription(), null);


        return null;
    }
}
