package com.ssafy.core.repository;

import com.ssafy.core.code.JoinCode;
import com.ssafy.core.code.YNCode;
import com.ssafy.core.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository

public interface UserRepoCommon {

    User findUserLogin(String email, JoinCode type);

    User findByUserId(String id);

    void deleteUser(User user);

}






































