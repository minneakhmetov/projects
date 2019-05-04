package com.kursach.services;

import com.kursach.models.Auth;
import com.kursach.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {

    private AuthRepository authRepository;
    @Autowired
    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean isExistByCookie(Auth auth) {
        return authRepository.read(auth.getUserId()).contains(auth);
    }

    public void delete(Auth auth){
        authRepository.delete(auth);
    }
}
