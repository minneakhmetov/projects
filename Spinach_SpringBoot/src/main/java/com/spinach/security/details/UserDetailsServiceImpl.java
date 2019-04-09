package com.spinach.security.details;

import com.spinach.dao.UsersRepository;
import com.spinach.exceptions.UserNotFoundException;
import com.spinach.models.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            LoginModel model = usersRepository.readByEmail(s);
            return new UserDetailsImpl(model);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
