package com.ssafy.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookListInfoResDTO {
    @ApiModelProperty(value = "title", required = true, example = "")
    private String title;

    @ApiModelProperty(value = "커버", required = true, example = "")
    private String cover;

    @ApiModelProperty(value = "책id", required = true, example = "")
    private long bookId;
}
