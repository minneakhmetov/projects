package com.spinach.services;

import com.spinach.exceptions.UserNotFoundException;
import com.spinach.forms.LoginForm;
import com.spinach.models.AuthModel;
import com.spinach.models.LoginModel;
import com.spinach.dao.AuthRepository;
import com.spinach.dao.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Deprecated
@Service
public class AuthService {
    private UsersRepository usersRepository;
    private PasswordEncoder encoder;
    private AuthRepository authRepository;

//    private static final String PATH = "C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_node\\public\\";
//
//    private static final String URL = "http://localhost:8080/";
    @Autowired
    public AuthService(UsersRepository usersRepository, PasswordEncoder encoder, AuthRepository authRepository) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
        this.authRepository = authRepository;
    }

    public AuthModel auth(LoginForm form) {
        LoginModel model = null;
        try {
            model = usersRepository.readByEmail(form.getEmail());
        } catch (UserNotFoundException e) {
            return null;
        }
        if(encoder.matches(form.getPassword(), model.getHashPassword())){
            AuthModel authModel = AuthModel.builder()
                    .cookieValue(UUID.randomUUID().toString())
                    .id(model.getId())
                    .build();
            authRepository.save(authModel);
            return authModel;
        } else return null;
    }

    public boolean existByCookie(AuthModel model){
        AuthModel authModel = null;
        try {
            authModel = authRepository.readById(model.getId());
        } catch (UserNotFoundException e) {
            return false;
        }
        return authModel.getCookieValue().equals(model.getCookieValue());
    }
}
