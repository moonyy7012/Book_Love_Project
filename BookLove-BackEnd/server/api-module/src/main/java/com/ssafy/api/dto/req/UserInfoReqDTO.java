package com.ssafy.api.dto.req;

import com.ssafy.core.entity.Category;
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
public class UserInfoReqDTO {

    @NotBlank
    @ApiModelProperty(value = "gender", required = true, example = "남자")
    private String gender;

    @NotNull
    @ApiModelProperty(value = "age", required = true, example = "26")
    private int age;

    @ApiModelProperty(value = "category", required = true, example = "소설")
    private List<String> categories;
}
