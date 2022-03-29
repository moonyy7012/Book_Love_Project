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
public class BookListResDTO {
    @ApiModelProperty(value = "베스트셀러", required = true, example = "")
    private List<BookListInfoResDTO> bestseller;





}
