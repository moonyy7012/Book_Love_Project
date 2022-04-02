package com.ssafy.api.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInfoReqDTO {

    @NotBlank
    @ApiModelProperty(value = "nickname", required = true, example = "박싸피")
    private String nickname;

}
