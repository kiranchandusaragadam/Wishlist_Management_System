package com.springboot.Wishlist.management.system.controller;

import com.springboot.Wishlist.management.system.dto.request.WishlistRequest;
import com.springboot.Wishlist.management.system.dto.response.GeneralResponse;
import com.springboot.Wishlist.management.system.dto.response.WishlistResponse;
import com.springboot.Wishlist.management.system.exception.CustomException;
import com.springboot.Wishlist.management.system.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    // api to add wishlist item into db
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WishlistResponse> addWishlistItem(@RequestBody WishlistRequest wishlistRequest) throws CustomException {
        WishlistResponse newWishlistItem = wishlistService.addWishlistItem(wishlistRequest);
        return new ResponseEntity<>(newWishlistItem, HttpStatus.CREATED);
    }

    // api to get current user wishlist
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<WishlistResponse>> getWishlist() throws CustomException {
        List<WishlistResponse> userWishlist= wishlistService.getWishlist();
        return new ResponseEntity<>(userWishlist, HttpStatus.OK);
    }

    // api to delete wishlist by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<GeneralResponse> deleteWishlistItemById(@PathVariable("id") int itemId) throws CustomException {
        wishlistService.deleteWishlistItemById(itemId);
        return new ResponseEntity<>(new GeneralResponse("Item deleted successfully", true), HttpStatus.OK);
    }
}
