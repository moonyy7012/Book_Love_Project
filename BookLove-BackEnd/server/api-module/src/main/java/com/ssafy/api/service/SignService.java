package com.ssafy.api.service;

import com.ssafy.api.dto.req.UserInfoReqDTO;
import com.ssafy.api.dto.res.SocialUserResponseDTO;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.entity.Category;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.ApiMessageException;
import com.ssafy.core.repository.CategoryRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SignService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
    public User findById(String id) throws Exception{
        return userRepository.findById(id);
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

    public User findUserByIdType(String id, JoinCode type){
        return userRepository.findUserLogin(id, type);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public User updateUser(long userId, UserInfoReqDTO req){
        User user = userRepository.findById(userId).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );
        user.updateAge(req.getAge());
        user.updateGender(req.getGender());
        List<Category> categoryList = new ArrayList<>();
        for(int i = 0 ; i < req.getCategories().size() ; i++){
            categoryList.add(
                    categoryRepository.findCategoryByName(req.getCategories().get(i))
            );
        }
        user.updateCategory(categoryList);
        return user;
    }

    @Transactional(readOnly = false)
    public User enrollUserInfo(long userId, UserInfoReqDTO req){
        User user = userRepository.findById(userId).orElseThrow( () -> new ApiMessageException("존재하지 않는 회원정보입니다.") );

        if(!req.getCategories().isEmpty()){
            user.updateIsChecked(true);
        }
        user.updateAge(req.getAge());
        user.updateGender(req.getGender());
        List<Category> categoryList = new ArrayList<>();
        for(int i = 0 ; i < req.getCategories().size() ; i++){
            categoryList.add(
                    categoryRepository.findCategoryByName(req.getCategories().get(i))
            );
        }
        user.updateCategory(categoryList);

        return user;
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user){
        userRepository.deleteUser(user);
        userRepository.delete(user);
    }

    @Transactional(readOnly = false)
    public User socialLogin(String accessToken) {
        SocialUserResponseDTO socialUser = WebClient.create().get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ApiMessageException(401, "소셜 토큰이 유효하지 않습니다.")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ApiMessageException("내부 서버 에러")))
                .bodyToMono(SocialUserResponseDTO.class)
                .block();

        User user = userRepository.findUserLogin(socialUser.getId(), JoinCode.KAKAO);

        if (user == null) {
            user = User.builder()
                    .id(socialUser.getId())
                    .type(JoinCode.KAKAO)
                    .nickname(socialUser.getProperties().getNickname())
                    .isChecked(false)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

            user = userRepository.save(user);
        }

        return user;
    }

    public User findUserByRefreshToken(String token) {
        User user = userRepository.findUserByRefreshToken(token).orElseThrow(() -> new ApiMessageException(401, "존재하지 않는 토큰 정보입니다.") );

        return user;
    }


}


















































