package com.ssafy.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDTO {
    @NotBlank
    @ApiModelProperty(value = "uid ", required = true, example = "test")
    private String id;

    @NotNull
    @Pattern(regexp = "^(none|sns)$")
    @ApiModelProperty(value = "로그인 타입 (none, sns)", required = true, example = "sns")
    private String type;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;

    @ApiModelProperty(value = "토큰값", required = false, example="")
    private String token;


}
