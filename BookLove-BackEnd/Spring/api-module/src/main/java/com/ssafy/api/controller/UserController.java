package com.ssafy.api.controller;

import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.req.*;
import com.ssafy.api.dto.res.LoginResDTO;
import com.ssafy.api.dto.res.TokensResDTO;
import com.ssafy.api.service.UserService;
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
@RequestMapping(value = "/api/user")

public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    // 일반 회원가입(ID, PW만 입력)
    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    CommonResult userSignUp(@Valid @RequestBody SignUpReqDTO req) throws Exception {
        // uid 중복되는 값이 존재하는지 확인 (uid = 고유한 값)
        User uidChk = userService.findById(req.getId());
        if (uidChk != null) {
            throw new ApiMessageException("중복된 uid값의 회원이 존재합니다.");
        }

        User user = User.builder()
                .id(req.getId())
                .type(JoinCode.NONE)
                .nickname(req.getNickname())
                .password(passwordEncoder.encode(req.getPassword()))
                .isChecked(false)
                .gender("")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        long userId = userService.userSignUp(user);

        if (userId <= 0) {
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");
        }

        return responseService.getSuccessResult();
    }

    //retrun true -> 추가정보 입력완료 false-> 카테고리 미입력
    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "추가정보 입력", notes = "추가정보 입력")
    @PatchMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<Boolean> inputInfo(@Valid @RequestBody UserInfoReqDTO req, HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = userService.enrollUserInfo(Long.parseLong(userPk), req);
        userService.saveUser(user);
        boolean isChecked = user.isChecked();
        return responseService.getSingleResult(isChecked);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "닉네임 수정", notes = "닉네임 수정")
    @PatchMapping(value = "/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CommonResult updateNickname(@Valid @RequestBody UpdateNicknameReqDTO req, HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        userService.updateUserNickname(Long.parseLong(userPk), req);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 수정")
    @PatchMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CommonResult updatePassword(@Valid @RequestBody UpdatePasswordReqDTO req, HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = userService.findUserById(Long.parseLong(userPk));

        if (!passwordEncoder.matches(req.getPrePassword(), user.getPassword())) {
            throw new ApiMessageException("비밀번호가 일치하지 않습니다.");
        }

        user.updatePwd(passwordEncoder.encode(req.getNewPassword()));
        userService.saveUser(user);

        return responseService.getSuccessResult();
    }

    //아이디 중복 체크 true->중복O false->중복X
    @ApiOperation(value = "ID중복체크", notes = "ID중복체크")
    @GetMapping(value = "/idcheck/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<Boolean> checkId(@PathVariable String id) throws Exception {
        User uidChk = userService.findById(id);

        boolean isOverlapped = true;
        if (uidChk == null) {
            isOverlapped = false;
        }
        return responseService.getSingleResult(isOverlapped);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginResDTO> userLogin(@Valid LoginReqDTO req) throws Exception {
        User user = userService.findUserByIdType(req.getId(), JoinCode.NONE);
        if (user == null) {
            throw new ApiMessageException("아이디를 잘못 입력하였습니다.");
        } else if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ApiMessageException("비밀번호를 잘못 입력하였습니다.");
        }

        LoginResDTO dto = LoginResDTO.builder()
                .id(user.getUserId())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .userCategoryList(user.changeToCategoryNameList())
                .isChecked(user.isChecked())
                .userId(user.getId())
                .type(user.getType().toString())
                .build();

        List<String> list = Arrays.asList("ROLE_USER");
        dto.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
        dto.setRefreshToken(jwtTokenProvider.createRefreshToken());

        user.updateAccessToken(dto.getAccessToken());
        user.updateRefreshToken(dto.getRefreshToken());

        userService.saveUser(user);

        return responseService.getSingleResult(dto);
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "자동 로그인", notes = "자동 로그인")
    @GetMapping(value = "/auto", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginResDTO> autoLogin(HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = userService.findUserByIdWithCategory(Long.parseLong(userPk));

        LoginResDTO dto = LoginResDTO.builder()
                .id(user.getUserId())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .userCategoryList(user.changeToCategoryNameList())
                .isChecked(user.isChecked())
                .userId(user.getId())
                .type(user.getType().toString())
                .build();

        List<String> list = Arrays.asList("ROLE_USER");
        dto.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
        dto.setRefreshToken(jwtTokenProvider.createRefreshToken());

        user.updateAccessToken(dto.getAccessToken());
        user.updateRefreshToken(dto.getRefreshToken());

        userService.saveUser(user);

        return responseService.getSingleResult(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "Kakao Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "소셜 로그인", notes = "소셜 로그인")
    @PostMapping(value = "/social", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<LoginResDTO> socialLogin(HttpServletRequest request) throws Exception {
        User user = userService.socialLogin(request.getHeader("X-Auth-Token"));

        LoginResDTO dto = LoginResDTO.builder()
                .id(user.getUserId())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .userCategoryList(user.changeToCategoryNameList())
                .isChecked(user.isChecked())
                .userId(user.getId())
                .type(user.getType().toString())
                .build();

        List<String> list = Arrays.asList("ROLE_USER");
        dto.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
        dto.setRefreshToken(jwtTokenProvider.createRefreshToken());

        user.updateAccessToken(dto.getAccessToken());
        user.updateRefreshToken(dto.getRefreshToken());
        userService.saveUser(user);

        return responseService.getSingleResult(dto);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "refresh Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "접근 토큰 재발급", notes = "접근 토큰 재발급")
    @PatchMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<TokensResDTO> refreshToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("X-Auth-Token");
        User user = userService.findUserByRefreshToken(refreshToken);

        TokensResDTO tokensResDTO = TokensResDTO.builder().build();
        if (jwtTokenProvider.validateToken(user.getRefreshToken())) {
            List<String> list = Arrays.asList("ROLE_USER");
            tokensResDTO.setAccessToken(jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()), list));
            tokensResDTO.setRefreshToken(jwtTokenProvider.createRefreshToken());

            user.updateAccessToken(tokensResDTO.getAccessToken());
            user.updateRefreshToken(tokensResDTO.getRefreshToken());
            userService.saveUser(user);
        } else {
            throw new ApiMessageException(-9999, "알 수 없는 오류가 발생하였습니다.");
        }

        return responseService.getSingleResult(tokensResDTO);
    }
}
