package com.ssafy.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReqDTO {
    @NotBlank
    @ApiModelProperty(value = "nickname", required = true, example = "박싸피")
    private String nickname;

    @NotBlank
    @ApiModelProperty(value = "password", required = true, example = "123")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "gender", required = true, example = "남자")
    private String gender;

    @NotBlank
    @ApiModelProperty(value = "age", required = true, example = "26")
    private int age;






}
