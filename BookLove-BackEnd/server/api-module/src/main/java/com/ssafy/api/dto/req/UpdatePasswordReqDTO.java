package com.ssafy.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordReqDTO {
    @NotBlank
    @ApiModelProperty(value = "이전 비밀번호", required = true, example = "123")
    private String prePassword;

    @NotBlank
    @ApiModelProperty(value = "새 비밀번호", required = true, example = "1233")
    private String newPassword;
}
