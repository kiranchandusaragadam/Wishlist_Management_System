package com.springboot.Wishlist.management.system.repository;

import com.springboot.Wishlist.management.system.entity.User;
import com.springboot.Wishlist.management.system.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Integer> {
    // custom method to get wishlist items by user
    List<WishlistItem> findByUser(User user);
}
