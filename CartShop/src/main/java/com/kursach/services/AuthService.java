package com.kursach.services;

import com.kursach.models.Auth;

public interface AuthService {
    boolean isExistByCookie(Auth auth);
    void delete(Auth auth);
}
