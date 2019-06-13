package com.alpha.cache.starter;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class UserService {
    private List<User> users = new CopyOnWriteArrayList<>();

    {
        users.add(new User(1, "Kenan", 30));
        users.add(new User(2, "Mert", 35));
    }

    @Cacheable(value = "cache")
    public User getUserById(int id) {
        System.out.println("User with id " + id + " requested.");
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @CachePut(value = "cache", key = "#user.id")
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @CacheEvict(value = "cache")
    public void delete(int id) {
        users.forEach(user -> {
            if (user.getId() == id) users.remove(user);
        });
    }

    @CachePut(value = "cache", key = "#user.id")
    public User update(User user) {
        users.forEach(old -> {
            if (old.getId() == user.getId()) users.remove(old);
        });
        users.add(user);
        return user;
    }

    @Cacheable(value = "cache", unless = "#result.age>30")
    public User getUserByIdWithContionalCache(int id) {
        System.out.println("User with id " + id + " requested.");
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @CachePut(value = "cache", key = "#user.id", condition = "#user.age>30")
    public User addUserWithContionalCache(User user) {
        users.add(user);
        return user;
    }

    @Caching(
            evict = {@CacheEvict(value = "cache", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(value = "cache", key = "#user.id")}
    )
    public User resetCache(User user) {
        users.add(user);
        return user;
    }

    /**
     * designing this method aims to demonstrate using xml configuration instead of annotation in the case of you cant touch the source code
     */
    public List<User> getAllUsers() {
        System.out.println("query from database");
        return users;
    }
}
