/*
 * Developed by Razil Minneakhmetov on 12/27/18 12:56 AM.
 * Last modified 12/27/18 12:56 AM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import models.Auth;
import org.springframework.dao.EmptyResultDataAccessException;
import repositories.AuthRepository;
import repositories.DeleteRepository;

public class SignInService {
    private AuthRepository authRepository;
    private DeleteRepository deleteRepository;

    public SignInService(AuthRepository authRepository, DeleteRepository deleteRepository) {
        this.authRepository = authRepository;
        this.deleteRepository = deleteRepository;
    }
    public void signIn(){

    }
    public boolean checkRegistered(long vkId){
        try {
            authRepository.read(vkId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }
    public void deleteAll(long vkId){
        deleteRepository.deleteAll(vkId);
    }
    public void signIn(Auth auth){
        authRepository.save(auth);
    }
    public Auth auth(String value){
        return authRepository.read(value);
    }
}