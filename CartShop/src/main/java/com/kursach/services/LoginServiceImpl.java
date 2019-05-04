/*
 * Developed by Razil Minneakhmetov on 10/28/18 12:45 PM.
 * Last modified 10/28/18 12:45 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.services;

import com.kursach.dto.UserDto;
import com.kursach.models.Auth;
import com.kursach.models.User;
import com.kursach.repositories.AuthRepository;
import com.kursach.repositories.UserRepository;
import com.kursach.utils.VKAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.kursach.dto.UserDto.from;

@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    @Qualifier("userRepositoryHibernateImpl")
    private UserRepository repository;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDto loginOrRegister(String code){
        VKAuthService vk = new VKAuthService(code);
        User user = repository.readOne(Integer.valueOf(vk.getUserId()));
        if (user == null){
            UserDto dto = vk.auth();
            user  = User.builder()
                    .name(dto.getName())
                    .surname(dto.getSurname())
                    .photoURL(dto.getPhotoURL())
                    .vkId(dto.getVkId())
                    .build();
            repository.create(user);
        }
        String auth = UUID.randomUUID().toString();
        authRepository.save(Auth.builder().userId(user.getVkId()).auth(auth).build());
        return from(user, auth);
    }

    @Override
    public void logout(Auth auth) {
        authRepository.delete(auth);
    }

    @Override
    public UserDto getUser(Integer vkId){
        return from(repository.readOne(vkId));
    }

    @Override
    public boolean isExistByCookie(Auth auth) {
        return auth.equals(authRepository.read(auth.getUserId()));
    }


}