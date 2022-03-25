package com.ssafy.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDTO {
    //

    @ApiModelProperty(value = "회원 아이디", required = true, example = "1")
    private long id;
    @ApiModelProperty(value = "회원 닉네임", required = true, example = "닉네임")
    private String nickname;
    @ApiModelProperty(value = "회원 연령", required = true, example = "28")
    private int age;
    @ApiModelProperty(value = "회원 성별", required = true, example = "남자")
    private String gender;
    @ApiModelProperty(value = "회원 선호 카테고리", required = false, example = "")
    private List<String> userCategoryList;
    @ApiModelProperty(value = "토큰값", required = true, example = "")
    private String token;


}
