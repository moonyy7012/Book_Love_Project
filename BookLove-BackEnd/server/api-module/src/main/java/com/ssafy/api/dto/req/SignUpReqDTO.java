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
    @ApiModelProperty(value = "id", required = true, example = "kakao123")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "닉네임", required = true, example = "닉네임")
    private String nickname;


}
