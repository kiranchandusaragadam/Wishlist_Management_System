package com.springboot.Wishlist.management.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistResponse {
    private int id;

    private String name;

    private String description;

    private double price;

    private UserResponse userInfo;
}
