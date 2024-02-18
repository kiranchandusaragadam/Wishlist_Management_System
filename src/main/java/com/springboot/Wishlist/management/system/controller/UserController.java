package com.springboot.Wishlist.management.system.controller;

import com.springboot.Wishlist.management.system.dto.request.AuthRequest;
import com.springboot.Wishlist.management.system.dto.request.SignupRequest;
import com.springboot.Wishlist.management.system.dto.response.UserResponse;
import com.springboot.Wishlist.management.system.exception.CustomException;
import com.springboot.Wishlist.management.system.service.JwtService;
import com.springboot.Wishlist.management.system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // api to add new user (signup)
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody SignupRequest signupRequest){
        UserResponse newUser = userInfoService.addUser(signupRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // api to generate token while login
    @PostMapping("/login")
    public String loginAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getEmail());
        }
        else{
            throw new UsernameNotFoundException("Invalid user request!!");
        }
    }
}
