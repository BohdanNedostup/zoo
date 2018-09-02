package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zoowebapp.entity.User;
import zoowebapp.repository.UserRepository;
import zoowebapp.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findUserByEmail(email);
//        if(user == null) {
//            throw new UsernameNotFoundException("User with email: " + email + " not found");
//        }
//        return UserMapper.toSecurityUser(user);
//    }

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = this.userRepository.findUserByEmail(email);

        if (user == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        List<String> roleNames = userRoleRepository.findRolesByUserId(user.getId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), grantList);

        return userDetails;
    }

}
