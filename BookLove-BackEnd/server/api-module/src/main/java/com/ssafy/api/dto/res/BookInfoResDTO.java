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
    @ApiModelProperty(value = "bookId", required = true, example = "")
    private long bookId;

    @ApiModelProperty(value = "제목", required = true, example = "")
    private String title;

    @ApiModelProperty(value = "책 소개", required = true, example = "")
    private String description;

    @ApiModelProperty(value = "작가", required = true, example = "")
    private String author;

    @ApiModelProperty(value = "출판일", required = true, example = "")
    private String pubDate;

    @ApiModelProperty(value = "isbn", required = true, example = "")
    private String isbn;

    @ApiModelProperty(value = "세일가", required = true, example = "")
    private int priceSales;

    @ApiModelProperty(value = "판매가", required = true, example = "")
    private int priceStandard;

    @ApiModelProperty(value = "표지", required = true, example = "")
    private String cover;

    @ApiModelProperty(value = "구매 링크", required = true, example = "")
    private String link;

    @ApiModelProperty(value = "카테고리", required = true, example = "")
    private String categoryName;

    @ApiModelProperty(value = "출판사", required = true, example = "")
    private String publisher;

    @ApiModelProperty(value = "판매 점수", required = true, example = "")
    private int salesPoint;

    @ApiModelProperty(value = "리뷰 (1~10)", required = true, example = "")
    private int customerReviewRank;

    @ApiModelProperty(value = "유사 책 리스트", example = "")
    private List<BookListInfoResDTO> similarBooks;

    public void setBookInfo(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.author = book.getAuthor();
        this.pubDate = book.getPubDate();
        this.isbn = book.getIsbn();
        this.priceSales = book.getPriceSales();
        this.priceStandard = book.getPriceStandard();
        this.cover = book.getCover();
        this.link = book.getLink();
        this.categoryName = book.getCategoryName();
        this.publisher = book.getPublisher();
        this.salesPoint = book.getSalesPoint();
        this.customerReviewRank = book.getCustomerReviewRank();
    }
}
