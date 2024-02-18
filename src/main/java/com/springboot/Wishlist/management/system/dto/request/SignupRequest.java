package com.springboot.Wishlist.management.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequest {
    private String name;

    private String email;

    private String password;

    private String roles;
}
