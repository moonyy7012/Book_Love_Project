package com.ssafy.api.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchReqDTO {

    @NotBlank
    @ApiModelProperty(value = "검색어", required = true, example = "검색어")
    private String keyword;
}
