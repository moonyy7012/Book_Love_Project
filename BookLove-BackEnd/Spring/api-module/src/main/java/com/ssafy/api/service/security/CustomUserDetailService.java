package com.ssafy.api.service.security;


import com.ssafy.api.service.UserService;
import com.ssafy.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userPk){
        User user = null;
        try {
            user = userService.findUserById(Long.valueOf(userPk));
        } catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
}
