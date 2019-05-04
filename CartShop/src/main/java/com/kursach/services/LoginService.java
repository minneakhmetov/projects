/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:47 PM.
 * Last modified 12/21/18 6:47 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.services;

import com.kursach.dto.UserDto;
import com.kursach.models.Auth;

public interface LoginService {
    UserDto loginOrRegister(String code);
    void logout(Auth auth);
    UserDto getUser(Integer vkId);
    boolean isExistByCookie(Auth auth);
}