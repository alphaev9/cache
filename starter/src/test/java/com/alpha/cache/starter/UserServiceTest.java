package com.alpha.cache.starter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:spring-config.xml")
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CacheManager cacheManager;

    @Test
    void getUserById() {
        User user1 = userService.getUserById(1);
        System.out.println(user1);
        User user2 = userService.getUserById(1);
        System.out.println(user2);
    }

    @Test
    void addUser() {
        User jone = new User(3, "jone", 20);
        userService.addUser(jone);
        User user = userService.getUserById(3);
        System.out.println(user);
    }

    @Test
    void delete() {
        User kenan = userService.getUserById(1);
        userService.delete(kenan.getId());
        User user = userService.getUserById(kenan.getId());
        System.out.println(user);
    }

    @Test
    void update() {
        User user1 = userService.getUserById(1);
        System.out.println(user1);
        User kenan = new User(1, "Kenan", 20);
        userService.update(kenan);
        User user = userService.getUserById(1);
        System.out.println(user);

    }

    @Test
    void getUserByIdWithContionalCache() {
        User user = userService.getUserByIdWithContionalCache(1);
        System.out.println(user);
        User user1 = userService.getUserByIdWithContionalCache(1);
        System.out.println(user1);
    }

    @Test
    void addUserWithContionalCache() {
        User jone = new User(3, "jone", 20);
        userService.addUserWithContionalCache(jone);
        User user = userService.getUserById(3);
        System.out.println(user);
    }

    @Test
    void cacheManager() {
        Cache cache = cacheManager.getCache("cache");
        cache.put(1, new User(1, "jone", 20));
        User user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    void resetCache() {
        Cache cache = cacheManager.getCache("cache");
        User kenan = new User(1, "Kenan", 30);
        User jone = new User(3, "jone", 20);
        cache.put(1, kenan);
        userService.resetCache(jone);
        User user = userService.getUserById(3);
        System.out.println(user);
    }
}