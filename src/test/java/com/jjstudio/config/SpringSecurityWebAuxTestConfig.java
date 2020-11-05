package com.jjstudio.config;

import com.jjstudio.config.auth.UserPrincipal;
import com.jjstudio.model.User;
import com.jjstudio.util.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsServiceTest() {
        User freeUser = new User("freeuser@gmail.com", "pwd", new Date(), "Free", "User");
        UserPrincipal freeUserPrincipal = new UserPrincipal(freeUser, Collections.singletonList(
                new SimpleGrantedAuthority(Role.FREE_USER.toString())
        ));

        User paidUser = new User("paiduser@gmail.com", "pwd", new Date(), "Paid", "User");
        UserPrincipal paidUserPrincipal = new UserPrincipal(paidUser, Collections.singletonList(
                new SimpleGrantedAuthority(Role.PAID_USER.toString())
        ));

        User adminUser = new User("adminuser@gmail.com", "pwd", new Date(), "Admin", "User");
        UserPrincipal adminUserPrincipal = new UserPrincipal(adminUser, Collections.singletonList(
                new SimpleGrantedAuthority(Role.ADMIN.toString())
        ));

        return new InMemoryUserDetailsManager(Arrays.asList(
                freeUserPrincipal, paidUserPrincipal, adminUserPrincipal
        ));
    }

}
