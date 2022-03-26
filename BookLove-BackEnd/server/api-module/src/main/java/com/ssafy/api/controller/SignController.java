package com.ssafy.api.controller;

import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.req.LoginReqDTO;
import com.ssafy.api.dto.req.SignUpReqDTO;
import com.ssafy.api.dto.req.UserInfoReqDTO;
import com.ssafy.api.dto.res.LoginResDTO;
import com.ssafy.api.dto.res.TokensResDTO;
import com.ssafy.api.service.SignService;
import com.ssafy.api.service.common.CommonResult;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Api(tags = {"01. 가입, 로그인"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")

public class SignController {
    private final SignService signService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;


    // 일반 회원가입(ID, PW만 입력)
    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    CommonResult userSignUp(@Valid SignUpReqDTO req) throws Exception{
        // uid 중복되는 값이 존재하는지 확인 (uid = 고유한 값)
        User uidChk = signService.findById(req.getId());
        if(uidChk != null)
            throw new ApiMessageException("중복된 uid값의 회원이 존재합니다.");
        // DB에 저장할 User Entity 세팅
        User user = User.builder()
                .id(req.getId())
                .type(JoinCode.NONE)
                .nickname(req.getNickname())
                .password(passwordEncoder.encode(req.getPassword()))
                .isChecked(false)
                // 기타 필요한 값 세팅
                .roles(Collections.singletonList("ROLE_USER"))
                .build(); // 인증된 회원인지 확인하기 위한 JWT 토큰에 사용될 데이터
        // 회원가입 (User Entity 저장)
        long userId = signService.userSignUp(user);

        // 저장된 User Entity의 PK가 없을 경우 (저장 실패)
        if(userId <= 0)
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");

        return responseService.getSuccessResult();
    }
    //retrun true -> 추가정보 입력완료 false-> 카테고리 미입력
    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "추가정보 입력", notes = "추가정보 입력")
    @PostMapping(value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<Boolean> inputInfo(@Valid UserInfoReqDTO req, HttpServletRequest request)throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = signService.enrollUserInfo(Long.parseLong(userPk), req);
        signService.saveUser(user);
        boolean isCheck = user.isChecked();
        return responseService.getSingleResult(isCheck);
    }

    //아이디 중복 체크 true->중복O false->중복X
    @ApiOperation(value = "ID중복체크", notes = "ID중복체크")
    @PostMapping(value = "/user/idcheck/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<Boolean> checkId(@PathVariable String id)throws Exception{
        User uidChk = signService.findById(id);

        boolean isOverlaped = true;
        if (uidChk == null) {
            isOverlaped = false;
        }
        return responseService.getSingleResult(isOverlaped);
    }

//    //회원정보 수정
//    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
//    @PutMapping(value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    CommonResult updateUser(@Valid UserInfoReqDTO req, HttpServletRequest request) throws Exception {
//        String token = jwtTokenProvider.resolveToken(request);
//        String userPk = jwtTokenProvider.getUserPk(token);
//        User user = signService.updateUser(Long.parseLong(userPk), req);
//        signService.saveUser(user);
//        return responseService.getSuccessResult();
//    }

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
    CommonResult deleteUser(@PathVariable long userId) throws Exception {
        User user = signService.findUserById(userId);
        signService.deleteUser(user);
        return responseService.getSuccessResult();
    }


    //
    @ApiOperation(value = "로그인", notes = "로그인")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginResDTO> userLogin(@Valid LoginReqDTO req) throws Exception {
        // uid 중복되는 값이 존재하는지 확인 (uid = 고유한 값)\
        User user = signService.findUserByIdType(req.getId(), JoinCode.NONE);
        if (user == null) {
            throw new ApiMessageException("아이디가 틀렸다");
        } else if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ApiMessageException("비밀번호가 틀렸다");
        }

        LoginResDTO dto = LoginResDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .userCategoryList(user.changeToCategoryNameList())
                .isChecked(user.isChecked())
                .build();

        List<String> list = Arrays.asList("ROLE_USER");
        dto.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
        dto.setRefreshToken(jwtTokenProvider.createRefreshToken());

        user.updateAccessToken(dto.getAccessToken());
        user.updateRefreshToken(dto.getRefreshToken());

        signService.saveUser(user);

        return responseService.getSingleResult(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "header", value = "Kakao Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "소셜 로그인", notes = "소셜 로그인")
    @PostMapping(value = "/user/social", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginResDTO> socialLogin(HttpServletRequest request) throws Exception {
        User user = signService.socialLogin(request.getHeader("header"));

        LoginResDTO dto = LoginResDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .userCategoryList(user.changeToCategoryNameList())
                .isChecked(user.isChecked())
                .build();

        List<String> list = Arrays.asList("ROLE_USER");
        dto.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
        dto.setRefreshToken(jwtTokenProvider.createRefreshToken());

        user.updateAccessToken(dto.getAccessToken());
        user.updateRefreshToken(dto.getRefreshToken());
        signService.saveUser(user);

        return responseService.getSingleResult(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "header", value = "refresh Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "접근 토큰 재발급", notes = "접근 토큰 재발급")
    @PutMapping(value = "/user/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<TokensResDTO> refreshToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("header");
        User user = signService.findUserByRefreshToken(refreshToken);

        TokensResDTO tokensResDTO = TokensResDTO.builder().build();
        if (jwtTokenProvider.validateToken(user.getRefreshToken())) {
            List<String> list = Arrays.asList("ROLE_USER");
            tokensResDTO.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
            tokensResDTO.setRefreshToken(jwtTokenProvider.createRefreshToken(refreshToken));

            user.updateAccessToken(tokensResDTO.getAccessToken());
            user.updateRefreshToken(tokensResDTO.getRefreshToken());
            signService.saveUser(user);
        } else {
            throw new ApiMessageException(401, "유효하지 않은 토큰 정보입니다.");
        }

        return responseService.getSingleResult(tokensResDTO);
    }
}
