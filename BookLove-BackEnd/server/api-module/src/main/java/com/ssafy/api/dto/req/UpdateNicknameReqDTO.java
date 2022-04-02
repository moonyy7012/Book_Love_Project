package com.ssafy.api.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameReqDTO {
    @NotBlank
    @ApiModelProperty(value = "nickname", required = true, example = "박싸피")
    private String nickname;
}
