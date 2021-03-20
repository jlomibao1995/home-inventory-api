package com.homeinventory.homeinventory.security;

import com.homeinventory.homeinventory.user.User;
import com.homeinventory.homeinventory.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> user = userRepository.findUserByEmail(email);

        if (!user.isPresent()){
            throw new UsernameNotFoundException(email);
        }
        return new MyUserPrincipal(user.get());
    }
}
