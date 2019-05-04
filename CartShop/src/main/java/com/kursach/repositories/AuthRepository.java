package com.kursach.repositories;

import com.kursach.models.Auth;

import java.util.List;

public interface AuthRepository {
    void save(Auth auth);
    void delete(Auth auth);
    List<Auth> read(Integer vkId);


}
