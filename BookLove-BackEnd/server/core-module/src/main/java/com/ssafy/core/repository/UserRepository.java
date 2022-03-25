package com.ssafy.core.repository;

import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepoCommon{
    Optional<User> findUserByRefreshToken(String token);
}
