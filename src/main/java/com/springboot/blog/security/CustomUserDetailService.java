package com.springboot.blog.security;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//this is to implement database security
@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //this is logic to load user from user
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found" + usernameOrEmail));

        /*Note that above user is user created by us, so now we need to create user that is
        provided by spring  */
        return new org.springframework.security.core.userdetails.
                User(user.getEmail(),user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //method to map set of roles to collection of granted authorities...
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
       return roles.stream()
               .map(role->new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toList());
    }
}
