package io.bootify.my_app.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.bootify.my_app.model.Appuser;
import io.bootify.my_app.repos.AppuserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;



@Service
public class AppuserDetailsServiceImpl implements UserDetailsService {

    private AppuserRepository appuserRepository;

    public AppuserDetailsServiceImpl(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Appuser appuser = appuserRepository.findAppuserByUsername(username);

        if (appuser == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(
                appuser.getUsername()).password(appuser.getPasswordHash()).roles("USER").build();

        return userDetails;
    }
}