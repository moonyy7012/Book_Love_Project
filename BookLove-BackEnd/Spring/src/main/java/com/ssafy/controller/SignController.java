package com.ssafy.controller;

import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.req.LoginReqDTO;
import com.ssafy.api.dto.req.SignUpReqDTO;
import com.ssafy.api.dto.req.UserInfoReqDTO;
import com.ssafy.api.dto.res.LoginResDTO;
import com.ssafy.api.dto.res.UserIdResDTO;
import com.ssafy.api.dto.res.UserInfoResDTO;
import com.ssafy.api.service.AwsS3Service;
import com.ssafy.api.service.SignService;
import com.ssafy.api.service.StudyClassService;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.ApiMessageException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Api(tags = {"01. 가입"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign")

public class SignController {
    private final SignService signService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudyClassService studyClassService;
    private final AwsS3Service awsS3Service;

    /**
     * 로그인 : get /login
     * 회원가입 일반 : post /signup
     * 회원가입 후 프로필등록 : post /regProfile
     * 소셜 가입 여부 체크 : get /exists/social
     */


    // 회원가입
    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<UserIdResDTO> userSignUp(@Valid SignUpReqDTO req) throws Exception{
        // uid 중복되는 값이 존재하는지 확인 (uid = 고유한 값)
        User uidChk = signService.findByUid(req.getUid());
        if(uidChk != null)
            throw new ApiMessageException("중복된 uid값의 회원이 존재합니다.");

        // DB에 저장할 User Entity 세팅
        User user = User.builder()
                .uid(req.getUid())
                .joinType(JoinCode.valueOf(req.getType()))
                .name(req.getName())
                .pwd(passwordEncoder.encode(req.getPassword()))
                .intro(req.getIntro())
                .email(req.getEmail() == null ? "" : req.getEmail())
                .avatarSrc(req.getAvatarSrc())
                .location(req.getLocation())

                // 가입 후 프로필 등록으로 받을 데이터는 우선 기본값으로 세팅
                .score(0)


                // 기타 필요한 값 세팅
                .roles(Collections.singletonList("ROLE_USER"))
                .build(); // 인증된 회원인지 확인하기 위한 JWT 토큰에 사용될 데이터


        // 회원가입 (User Entity 저장)
        long userId = signService.userSignUp(user);
        // 저장된 User Entity의 PK가 없을 경우 (저장 실패)
        if(userId <= 0)
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");

        return responseService.getSingleResult(UserIdResDTO.builder().id(userId).build());
    }
    //이메일 체크
    @ApiOperation(value = "ID중복체크", notes = "ID중복체크")
    @PostMapping(value = "/checkEmail/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean checkId(@PathVariable String userEmail)throws Exception{
        User uidChk = signService.findByEmail(userEmail);
        if(uidChk == null){
            return true;
        }else{
            return false;
        }

    }


    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
    @PutMapping(value = "/user/update/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<UserInfoResDTO> updateUser(@Valid UserInfoReqDTO req, HttpServletRequest request, @PathVariable long userId) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String user = jwtTokenProvider.getUserPk(token);
        User user1 = signService.findUserById(Long.parseLong(user));

        user1.updateNickname(req.getName());
        user1.updatePwd(passwordEncoder.encode(req.getPwd()));
        user1.updateIntro(req.getIntro());
        user1.updateImg(req.getAvatarSrc());
        user1.updateLoacation(req.getLocation());
        signService.saveUser(user1);
        return responseService.getSingleResult(UserInfoResDTO.builder().id(user1.getUserId()).build());
    }

    //userId로 회원정보 조회
    @ApiOperation(value = "회원 정보", notes = "회원 정보")
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult infoUser(@PathVariable long userId) throws Exception {
        User user = signService.findUserById(userId);
        return responseService.getSingleResult(user);

    }

    //회원 삭제
    @ApiOperation(value = "회원 정보 삭제", notes = "회원 정보 삭제")
    @DeleteMapping(value = "/user/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<UserInfoResDTO> deleteUser(@PathVariable long userId) throws Exception {
        User user = signService.findUserById(userId);
        signService.deleteUser(user);
        return responseService.getSingleResult(UserInfoResDTO.builder().id(user.getUserId()).build());
    }

    /////////////

    @ApiOperation(value = "로그인", notes = "로그인")
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody SingleResult<LoginResDTO> userLogin(@Valid LoginReqDTO req) throws Exception{
        // uid 중복되는 값이 존재하는지 확인 (uid = 고유한 값)\
        User user = signService.findUserByUidType(req.getEmail(), JoinCode.valueOf(req.getType()));
        if(user == null){
            throw new ApiMessageException("아이디가 틀렸다");
        } else if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new ApiMessageException("비밀번호가 틀렸다");
        }
        LoginResDTO dto = LoginResDTO.builder()
                .email(user.getEmail())
                .uid(user.getUid())
                .userid(user.getUserId())
                .build();
        List<String> list = Arrays.asList("ROLE_USER");
        dto.setToken(jwtTokenProvider.createToken(String.valueOf(user.getUserId()), list));

        user.updateToken(req.getToken());
        signService.saveUser(user);


        return responseService.getSingleResult(dto);


    }





}
