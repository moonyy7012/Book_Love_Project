package com.ssafy.api.dto.res;


import com.ssafy.core.entity.Book;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoResDTO {

    @ApiModelProperty(value = "책", required = true, example = "")
    private Book bookInfo;
}
