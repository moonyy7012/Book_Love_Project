package com.ssafy.api.controller;



import com.ssafy.api.dto.res.BookInfoResDTO;
import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.BookListResDTO;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.common.ListResult;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import com.ssafy.core.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"02. 책"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class BookController {
    private final ResponseService responseService;
    private final BookService bookService;

    @ApiOperation(value = "책 상세정보", notes = "책 상세정보")
    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<BookInfoResDTO> getBookDetail(@PathVariable long bookId) throws Exception {
        BookInfoResDTO bookInfoResDTO = BookInfoResDTO.builder().build();

        bookInfoResDTO.setSimilarBooks(bookService.findSimilarBooks(bookId));
        Book book = bookService.findBook(bookId);
        bookInfoResDTO.setBookInfo(book);

        return responseService.getSingleResult(bookInfoResDTO);
    }

    @ApiOperation(value = "베스트셀러", notes = "베스트셀러")
    @GetMapping(value = "/book/bestseller/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getBestSeller(@PathVariable String categoryName)throws Exception{
        List<Book> bestseller = bookService.findBestseller(categoryName);
        //제목 커버 북아이디
        List<BookListInfoResDTO> infoLIst= new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(bestseller.get(i).getTitle())
                    .cover(bestseller.get(i).getCover())
                    .bookId(bestseller.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return responseService.getListResult(infoLIst);
    }



}
