package com.ssafy.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRecentListResDTO {
    @ApiModelProperty(value = "최근 클릭한 책들과 비슷한 책", required = true, example = "1")
    List<BookRecentInfoResDTO> bookRecentSimilarList;

    @ApiModelProperty(value = "최근 클릭한 책 리스트", required = true, example = "1")
    List<BookRecentInfoResDTO> bookRecentList;
}
