package com.ssafy.core.repository;

import com.ssafy.core.code.JoinCode;
import com.ssafy.core.entity.User;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepoCommon {

    User findUserLogin(String email, JoinCode type);

    User findById(String id);

    void deleteUser(User user);

}






































