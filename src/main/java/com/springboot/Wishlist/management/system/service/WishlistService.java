package com.springboot.Wishlist.management.system.service;

import com.springboot.Wishlist.management.system.dto.request.WishlistRequest;
import com.springboot.Wishlist.management.system.dto.response.UserResponse;
import com.springboot.Wishlist.management.system.dto.response.WishlistResponse;
import com.springboot.Wishlist.management.system.entity.User;
import com.springboot.Wishlist.management.system.entity.WishlistItem;
import com.springboot.Wishlist.management.system.exception.CustomException;
import com.springboot.Wishlist.management.system.repository.UserRepository;
import com.springboot.Wishlist.management.system.repository.WishlistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // method to add new wishlist item
    public WishlistResponse addWishlistItem(WishlistRequest wishlistRequest) throws CustomException {
        // convert wishlist request object into wishlist item object
        WishlistItem wishlistItem = modelMapper.map(wishlistRequest, WishlistItem.class);
        // now make relation b/w wishlist item and current user
        // get current logged-in user
        User currUser = getCurrentUser();

        // if user exists
        wishlistItem.setUser(currUser);
        // save this item in database
        wishlistRepository.save(wishlistItem);

        // create user response dto from current user
        UserResponse userResponse = modelMapper.map(currUser, UserResponse.class);

        // now link this user response to wishlist response
        WishlistResponse wishlistResponse = modelMapper.map(wishlistItem, WishlistResponse.class);
        wishlistResponse.setUserInfo(userResponse);

        return wishlistResponse;
    }

    // method to get all wishlist of current user
    public List<WishlistResponse> getWishlist() throws CustomException {
        // get the current logged-in user
        User currUser = getCurrentUser();

        // now get wishlist belongs to current user
        List<WishlistItem> wishlistItems = wishlistRepository.findByUser(currUser);
        List<WishlistResponse> wishlistResponseList = new ArrayList<>();

        // create user response dto from current user
        UserResponse userResponse = modelMapper.map(currUser, UserResponse.class);

        for(WishlistItem item : wishlistItems){
            WishlistResponse wishlistResponse = modelMapper.map(item, WishlistResponse.class);
            wishlistResponse.setUserInfo(userResponse);
            wishlistResponseList.add(wishlistResponse);
        }

        return wishlistResponseList;
    }

    // method to delete wishlist item by id
    public void deleteWishlistItemById(int itemId) throws CustomException {
        // get wishlistItem by id
        WishlistItem itemToBeDelete = wishlistRepository.findById(itemId).orElseThrow(() -> new CustomException("Item not found by this id"));

        // if we get item the check whether is belongs to current user or not
        User itemUser = itemToBeDelete.getUser();

        User currUser = getCurrentUser();

        if(itemUser.getEmail().equals(currUser.getEmail())){
            wishlistRepository.delete(itemToBeDelete);
        }else{
            throw new CustomException("This wishlist item not belongs to current user, so can't delete it");
        }
    }

    // common method to get current user
    public User getCurrentUser() throws CustomException {
        UserDetails userDetails = userInfoService.getCurrentUserDetails();
        if(userDetails == null){
            throw new CustomException("Current user not exists, please login!!!");
        }

        String userEmail = userDetails.getUsername();

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException("User not found"));

        return user;
    }
}
