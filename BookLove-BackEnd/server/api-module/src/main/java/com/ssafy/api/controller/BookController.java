package com.ssafy.api.controller;



import com.ssafy.api.dto.req.BookSearchReqDTO;
import com.ssafy.api.dto.res.BookInfoResDTO;
import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.BookListResDTO;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.common.ListResult;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import com.ssafy.core.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "책 상세정보", notes = "책 상세정보")
    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<BookInfoResDTO> getBookDetail(@PathVariable long bookId) throws Exception {
        BookInfoResDTO bookInfoResDTO = BookInfoResDTO.builder().build();

        bookInfoResDTO.setSimilarBooks(bookService.findSimilarBooks(bookId));
        Book book = bookService.findBook(bookId);
        bookInfoResDTO.setBookInfo(book);

        return responseService.getSingleResult(bookInfoResDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "베스트셀러", notes = "베스트셀러")
    @GetMapping(value = "/book/bestseller/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getBestSeller(@RequestParam("categoryName") String categoryName)throws Exception{
        List<Book> bestseller = bookService.findBestseller(categoryName);
        System.out.println(categoryName);
        List<BookListInfoResDTO> infoLIst= new ArrayList<>();
        for(int i = 0 ; i < bestseller.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(bestseller.get(i).getTitle())
                    .cover(bestseller.get(i).getCover())
                    .bookId(bestseller.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return responseService.getListResult(infoLIst);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "메인 베스트셀러", notes = "메인 베스트셀러")
    @GetMapping(value = "/book/bestseller/main", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getBestSeller()throws Exception{
        List<Book> bestseller = bookService.findMainBestseller();

        List<BookListInfoResDTO> infoLIst= new ArrayList<>();
        for(int i = 0 ; i < bestseller.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(bestseller.get(i).getTitle())
                    .cover(bestseller.get(i).getCover())
                    .bookId(bestseller.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return responseService.getListResult(infoLIst);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "신간리스트", notes = "신간리스트")
    @GetMapping(value = "/book/newbook", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getNewBook()throws Exception{
        List<Book> newBook = bookService.findNewBook();

        List<BookListInfoResDTO> infoLIst= new ArrayList<>();
        for(int i = 0 ; i < newBook.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(newBook.get(i).getTitle())
                    .cover(newBook.get(i).getCover())
                    .bookId(newBook.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return responseService.getListResult(infoLIst);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "검색결과", notes = "검색결과")
    @GetMapping(value = "/book/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getSearchList(@PathVariable String keyword)throws Exception{
        List<Book> searchResultByTitle = bookService.findBookByTitle(keyword);
        List<Book> searchResultByAuthor = bookService.findBookByAuthor(keyword);


        List<BookListInfoResDTO> searchResult= new ArrayList<>();
        List<BookListInfoResDTO> searchAuthorList= new ArrayList<>();
        //title 로 검색
        for(int i = 0 ; i < searchResultByTitle.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(searchResultByTitle.get(i).getTitle())
                    .cover(searchResultByTitle.get(i).getCover())
                    .bookId(searchResultByTitle.get(i).getBookId())
                    .build();
            searchResult.add(info);
        }
        //author 로 검색
        for(int i = 0 ; i < searchResultByAuthor.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(searchResultByAuthor.get(i).getTitle())
                    .cover(searchResultByAuthor.get(i).getCover())
                    .bookId(searchResultByAuthor.get(i).getBookId())
                    .build();
            searchAuthorList.add(info);
        }
        searchResult.addAll(searchAuthorList);
        return responseService.getListResult(searchResult);
    }


}
