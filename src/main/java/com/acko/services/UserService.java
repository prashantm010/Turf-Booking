package com.acko.services;

import com.acko.models.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserService {
    List<User> users = new ArrayList<>();

    public void createUser(User user) {
        // Logic to create a new user
        users.add(user);
    }
}
