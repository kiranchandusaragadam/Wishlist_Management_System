package com.springboot.Wishlist.management.system.service;

import com.springboot.Wishlist.management.system.dto.request.SignupRequest;
import com.springboot.Wishlist.management.system.dto.response.UserResponse;
import com.springboot.Wishlist.management.system.entity.User;
import com.springboot.Wishlist.management.system.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.findByEmail(username);

        // convert userInfo to userDetails and return
        return userInfo.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    // add new user details and return added user information
    public UserResponse addUser(SignupRequest signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User userInfo = modelMapper.map(signupRequest, User.class);
        userRepository.save(userInfo);
        return modelMapper.map(userInfo, UserResponse.class);
    }

    // method to get current logged-in user details
    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }
}
