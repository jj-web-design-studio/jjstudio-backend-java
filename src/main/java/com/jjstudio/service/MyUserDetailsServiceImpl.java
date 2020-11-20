package com.jjstudio.service;

import com.jjstudio.config.auth.UserPrincipal;
import com.jjstudio.model.User;
import com.jjstudio.resource.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user, getGrantedAuthority(user));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = user.getRole();
        if (role == null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            return grantedAuthorities;
        }
        switch(role) {
            case "ADMIN":
                grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                break;
            case "PAID_USER":
                grantedAuthorities.add(new SimpleGrantedAuthority("PAID_USER"));
                break;
            default:
                grantedAuthorities.add(new SimpleGrantedAuthority("FREE_USER"));
                break;
        }
        return grantedAuthorities;
    }
}
