package com.toshiro.dreamshops.Security.User;


import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepo.findByEmail(email))
                .orElseThrow(()->  new UsernameNotFoundException("user not found"));
        return ShopUserDetails.buildUserDetails(user);
    }


}
