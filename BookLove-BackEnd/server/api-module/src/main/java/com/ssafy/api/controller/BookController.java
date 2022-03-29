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
    public @ResponseBody
    SingleResult<BookInfoResDTO> getBookDetail(@PathVariable long bookId)throws Exception{

        Book book = bookService.findBook(bookId);
        BookInfoResDTO dto = BookInfoResDTO.builder()
                .bookInfo(book)
                .build();
//        String responseData = "";
//        String returnData = "";
//        String apiURL  = "http://localhost:8000/data/books/"+bookId;
//        URL url = new URL(apiURL);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
//        urlConnection.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
//        urlConnection.setRequestProperty("Accept", "application/json");
//        urlConnection.setRequestMethod("GET");
//        urlConnection.connect();
//        System.out.println(urlConnection.getContentType());

//        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//        StringBuffer  sb = new StringBuffer();
//        while ((responseData = br.readLine()) != null) {
//            sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
//        }
//        returnData = sb.toString();
//        System.out.println(returnData);
        return responseService.getSingleResult(dto);
    }

    @ApiOperation(value = "베스트셀러", notes = "베스트셀러")
    @GetMapping(value = "/book/bestseller/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ListResult<BookListInfoResDTO> getBestSeller(@PathVariable String categoryName)throws Exception{
        List<Book> bestseller = bookService.findBestseller(categoryName);
        //제목 커버 북아이디
        List<BookListInfoResDTO> infoLIst= new ArrayList<>();
        for(int i = 0 ; i<10 ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(bestseller.get(i).getTitle())
                    .cover(bestseller.get(i).getCover())
                    .bookId(bestseller.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }
        BookListResDTO list = BookListResDTO.builder()
                .bestseller(infoLIst)
                .build();

        return responseService.getListResult(infoLIst);
    }



}
