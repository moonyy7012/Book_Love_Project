package com.ssafy.api.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDTO {

    @NotBlank
    @ApiModelProperty(value = "카테고리이름", required = true, example = "소설")
    private String categoryName;
}
