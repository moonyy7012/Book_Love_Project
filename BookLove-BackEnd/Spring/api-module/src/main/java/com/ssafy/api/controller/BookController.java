package com.ssafy.api.controller;



import com.ssafy.api.config.security.JwtTokenProvider;
import com.ssafy.api.dto.res.BookInfoResDTO;
import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.BookMainListResDTO;
import com.ssafy.api.service.BookService;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.common.ListResult;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import com.ssafy.core.entity.Book;
import com.ssafy.core.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "책 상세정보", notes = "책 상세정보")
    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<BookInfoResDTO> getBookDetail(@PathVariable long bookId, HttpServletRequest request) throws Exception {
        BookInfoResDTO bookInfoResDTO = BookInfoResDTO.builder().build();

        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);

        bookInfoResDTO.setSimilarBooks(bookService.findSimilarBooks(bookId));
        Book book = bookService.findBook(bookId);

        bookService.putClickLog(Long.parseLong(userPk), book);
        bookInfoResDTO.setBookInfo(book);

        return responseService.getSingleResult(bookInfoResDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "베스트셀러", notes = "베스트셀러")
    @GetMapping(value = "/book/bestseller", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getBestSeller(@RequestParam(value="categoryName") String categoryName)throws Exception{
        List<Book> bestseller = bookService.findBestseller(categoryName);
        List<BookListInfoResDTO> infoLIst = new ArrayList<>();

        for (int i = 0 ; i < bestseller.size(); i++) {
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
    @ApiOperation(value = "메인 리스트", notes = "메인 베스트셀러")
    @GetMapping(value = "/book/bestseller/main", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SingleResult<BookMainListResDTO> getBestSeller(HttpServletRequest request) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(token);

        User user = userService.findUserByIdWithCategory(Long.parseLong(userPk));

        BookMainListResDTO bookMainListResDTO = BookMainListResDTO.builder()
                .bookBestSellerList(bookService.findMainBestseller())
                .bookNewList(bookService.findNewBook())
                .bookCategoryList(bookService.findBestsellerByCategoryList(user))
                .bookGenderAgeList(bookService.findBookByGenderAndAgeClickLog(user.getGender(), user.getAge()))
                .build();

        return responseService.getSingleResult(bookMainListResDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-Auth-Token", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "검색결과", notes = "검색결과")
    @GetMapping(value = "/book/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<List<BookListInfoResDTO>> getSearchList(@PathVariable String keyword) throws Exception{
        List<Book> searchResultByTitle = bookService.findBookByTitle(keyword);
        List<Book> searchResultByAuthor = bookService.findBookByAuthor(keyword);
        List<BookListInfoResDTO> searchTitleList = new ArrayList<>();
        List<BookListInfoResDTO> searchAuthorList = new ArrayList<>();
        //title 로 검색
        for(int i = 0 ; i < searchResultByTitle.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(searchResultByTitle.get(i).getTitle())
                    .cover(searchResultByTitle.get(i).getCover())
                    .bookId(searchResultByTitle.get(i).getBookId())
                    .build();
            searchTitleList.add(info);
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
        List<List<BookListInfoResDTO>> searchResult =
                new ArrayList<>();
        searchResult.add(searchTitleList);
        searchResult.add(searchAuthorList);

        return responseService.getListResult(searchResult);
    }
}
