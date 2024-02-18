package com.springboot.Wishlist.management.system.repository;

import com.springboot.Wishlist.management.system.entity.User;
import com.springboot.Wishlist.management.system.entity.WishlistItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class WishlistRepositoryTest {
    // get repo instance
    @Autowired
    private WishlistRepository wishlistRepository;

    private WishlistItem wishlistItem;
    private User user;

    // initial configuration before calling every test
    @BeforeEach
    void setUp() {
        user = new User(1,"kiranchandu", "user1@gmail.com", "123456", "USER", null);
        wishlistItem = new WishlistItem(1, "item1", "description1", 5999, user);

        wishlistRepository.save(wishlistItem);
    }

    // cleanup after executing every test
    @AfterEach
    void tearDown() {
        user = null;
        wishlistItem = null;
        wishlistRepository.deleteAll();
    }

    // valid user test case for findByUser method in repository
    @Test
    void testFindByUserWithValidUser(){
        List<WishlistItem> wishlistItemList = wishlistRepository.findByUser(user);

        // act and assert
        Assertions.assertEquals(user.getEmail(), wishlistItemList.get(0).getUser().getEmail());
    }

    // invalid user test case for findByUser method
    @Test
    void testFindByUserWithInvalidUser(){
        user = new User(2, "user2", "user2@gmail.com", "123", "USER", null);
        List<WishlistItem> wishlistItemList2 = wishlistRepository.findByUser(user);

        // act and assert
        Assertions.assertTrue(wishlistItemList2.size() == 0);
    }
}
