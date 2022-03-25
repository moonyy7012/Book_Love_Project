package com.ssafy.api.dto.res;

import com.ssafy.core.code.YNCode;

import com.ssafy.core.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDTO {
    @ApiModelProperty(value = "회원 PK", required = true, example = "1")
    private Long userId;

    @ApiModelProperty(value = "회원 닉네임", required = true, example = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "회원 연령", required = true, example = "28")
    private int age;

    @ApiModelProperty(value = "회원 성별", required = true, example = "남자")
    private String gender;

    @ApiModelProperty(value = "회원 선호 카테고리", required = false, example = "")
    private List<String> userCategoryList;

    @ApiModelProperty(value = "회원 정보 수집 여부", required = true, example = "")
    private boolean isChecked;

    @ApiModelProperty(value = "Refresh Token 값", required = true, example = "")
    private String refreshToken;

    @ApiModelProperty(value = "Access Token 값", required = true, example = "")
    private String accessToken;
}
