/*
 * Developed by Razil Minneakhmetov on 10/28/18 12:45 PM.
 * Last modified 10/28/18 12:45 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import forms.LoginForm;
import models.User;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

public class LoginServiceImpl implements LoginService {
    private UserRepository repository;

    public LoginServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public User login(LoginForm loginForm){
        User user = repository.readOne(loginForm.getVkId());
        if (user == null){
            user  = User.builder()
                    .name(loginForm.getName())
                    .photoURL(loginForm.getPhotoURL())
                    .vkId(loginForm.getVkId())
                    .build();
            repository.create(user);
        }
        return user;
    }
}