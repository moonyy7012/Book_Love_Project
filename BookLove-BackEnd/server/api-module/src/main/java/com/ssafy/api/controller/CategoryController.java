package com.ssafy.api.controller;


import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.req.CategoryReqDTO;
import com.ssafy.api.dto.req.SignUpReqDTO;
import com.ssafy.api.service.CategoryService;
import com.ssafy.api.service.SignService;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.core.entity.User;
import com.ssafy.core.entity.UserCategory;
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

@Api(tags = {"02. 카테고리"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class CategoryController {
    private final SignService signService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryService categoryService;

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "카테고리 등록", notes = "카테고리 등록")
    @PostMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    boolean enrollCategory(@Valid CategoryReqDTO req, HttpServletRequest request) throws Exception{
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = signService.findUserById(Long.parseLong(userPk));
        UserCategory userCategory= UserCategory.builder()
                .categoryName(req.getCategoryName())
                .user(user)
                .build();

        long userCategoryId = categoryService.enrollCategory(userCategory);
        if(userCategoryId<=0){
            throw new ApiMessageException("회원가입에 실패했습니다. 다시 시도해 주세요.");
        }
        return true;
    }


    @ApiOperation(value = "카테고리 정보 삭제", notes = "카테고리 정보 삭제")
    @DeleteMapping(value = "/category/{userCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    boolean deleteCategory(@PathVariable long userCategoryId)throws Exception{

        UserCategory userCategory = categoryService.findCategoryByCategoryId(userCategoryId);
        categoryService.deleteCategory(userCategory);
        if(userCategory.getUserCategoryId()<=0){
            throw new ApiMessageException("삭제실패");
        }
        return true;
    }


}
