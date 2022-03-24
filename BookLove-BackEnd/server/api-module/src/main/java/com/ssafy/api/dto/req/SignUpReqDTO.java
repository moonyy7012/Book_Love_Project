package com.ssafy.api.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO {
    @NotBlank
    @ApiModelProperty(value = "uid (일반회원:아이디, sns로그인:uid값)", required = true, example = "kakao123")
    private String uid;

    @NotNull
    @Pattern(regexp = "^(none|sns)$")
    @ApiModelProperty(value = "회원가입 타입 (none, sns)", required = true, example = "sns")
    private String type;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @ApiModelProperty(value = "이름", required = false, example = "카카오")
    private String nickname;


    @ApiModelProperty(value = "성별", required = false, example = "남자")
    private String gender;

    @ApiModelProperty(value = "나이", required = true, example = "26")
    private int age;





}
