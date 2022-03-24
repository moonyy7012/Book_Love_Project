package com.ssafy.api.service;

import com.ssafy.api.dto.req.UserInfoReqDTO;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.code.YNCode;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.ApiMessageException;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;


    /**
     * id로 회원정보 조회
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public User findUserById(long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );
        return user;
    }

    /**
     * uid로 user 조회
     * @param id
     * @return
     * @throws Exception
     */
    public User findByUid(String id) throws Exception{
        return userRepository.findByUserId(id);
    }


    /**
     * 회원가입 후 userId 리턴
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public long userSignUp(User user){
        User signUpUser = userRepository.save(user);
        return signUpUser.getUserId();
    }

    public User findUserByUidType(String email, JoinCode type){
        return userRepository.findUserLogin(email, type);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public User updateUser(long id, UserInfoReqDTO req){
        User user = userRepository.findById(id).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );
        user.updateNickname(req.getNickname());
        user.updatePwd(req.getPassword());
        user.updateAge(req.getAge());
        user.updateGender(req.getGender());
        return user;
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user){
        userRepository.deleteUser(user);
        userRepository.delete(user);
    }



}


















































