package com.ssafy.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokensResDTO {
    @ApiModelProperty(value = "새로 발급된 Refresh Token", required = true, example = "123131")
    String refreshToken;

    @ApiModelProperty(value = "새로 발급된 Access Token", required = true, example = "123123123")
    String accessToken;
}
