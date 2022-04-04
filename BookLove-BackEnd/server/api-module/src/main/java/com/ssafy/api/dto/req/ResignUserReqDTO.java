package com.ssafy.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResignUserReqDTO {
    @NotBlank
    @ApiModelProperty(value = "비밀번호", required = true, example = "123")
    private String password;
}
