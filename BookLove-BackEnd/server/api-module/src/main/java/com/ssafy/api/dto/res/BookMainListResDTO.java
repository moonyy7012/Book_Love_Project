package com.ssafy.api.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookMainListResDTO {
    @ApiModelProperty(value = "베스트셀러", required = true, example = "1")
    List<BookListInfoResDTO> bookBestSellerList;

    @ApiModelProperty(value = "신간", required = true, example = "1")
    List<BookListInfoResDTO> bookNewList;

    @ApiModelProperty(value = "카테고리", required = true, example = "1")
    List<BookListInfoResDTO> bookCategoryList;

    @ApiModelProperty(value = "연령/성별", required = true, example = "1")
    List<BookListInfoResDTO> bookGenderAgeList;

    @ApiModelProperty(value = "최근 클릭한 책들과 비슷한 책", required = true, example = "1")
    List<BookListInfoResDTO> bookRecentSimilarList;
}
