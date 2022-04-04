package com.ssafy.api.controller;

import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.res.BookRecentListResDTO;
import com.ssafy.api.service.ClickLogService;
import com.ssafy.api.service.SignService;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import com.ssafy.core.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Api(tags = {"03. 클릭로그"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clicklog")
public class ClickLogController {
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SignService signService;
    private final ClickLogService clickLogService;

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "최근 본 책", notes = "최근 본 책")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<BookRecentListResDTO> getClickLog(HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);

        User user = signService.findUserByIdWithCategory(Long.parseLong(userPk));

        BookRecentListResDTO bookRecentListResDTO = BookRecentListResDTO.builder()
                .bookRecentList(clickLogService.findBookRecentList(user.getUserId()))
                .build();

        if (bookRecentListResDTO.getBookRecentList().size() != 0){
            bookRecentListResDTO.setBookRecentSimilarList(clickLogService.findRecentSimilarBooks(user.getUserId()));
        } else {
            bookRecentListResDTO.setBookRecentSimilarList(new ArrayList<>());
        }

        return responseService.getSingleResult(bookRecentListResDTO);
    }
}
