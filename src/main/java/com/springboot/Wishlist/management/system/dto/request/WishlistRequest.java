package com.springboot.Wishlist.management.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistRequest {
    private String name;

    private String description;

    private double price;
}
