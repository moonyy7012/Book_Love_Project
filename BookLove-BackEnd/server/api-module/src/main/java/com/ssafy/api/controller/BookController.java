package com.ssafy.api.controller;



import com.ssafy.api.dto.res.BookInfoResDTO;
import com.ssafy.api.service.common.ResponseService;
import com.ssafy.api.service.common.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Api(tags = {"02. 책"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class BookController {
    private final ResponseService responseService;

    @ApiOperation(value = "책 상세정보", notes = "책 상세정보")
    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<String> getBookDetail(@PathVariable int bookId)throws Exception{

        String responseData = "";
        String returnData = "";
        String apiURL  = "http://localhost:8000/data/books/"+bookId;
        URL url = new URL(apiURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
//        urlConnection.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        System.out.println(urlConnection.getContentType());

        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        StringBuffer  sb = new StringBuffer();
        while ((responseData = br.readLine()) != null) {
            sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
        }
//        BookInfoResDTO dto = BookInfoResDTO.builder()
//                .title(req.getTitle())
//                .description(req.getDescription())
//                .author(req.getAuthor())
//                .pub_date(req.getPub_date())
//                .isbn(req.getIsbn())
//                .price_sales(req.getPrice_sales())
//                .price_standard(req.getPrice_standard())
//                .cover(req.getCover())
//                .link(req.getLink())
//                .category_id(req.getCategory_id())
//                .category_name(req.getCategory_name())
//                .publisher(req.getPublisher())
//                .sales_point(req.getSales_point())
//                .customer_review_rank(req.getCustomer_review_rank())
//                .build();
        returnData = sb.toString();
        System.out.println(returnData);
        return responseService.getSingleResult(returnData);
    }

    @ApiOperation(value = "베스트셀러", notes = "베스트셀러")
    @GetMapping(value = "/book/bestSeller", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SingleResult<String> getBestSeller()throws Exception{
        String responseData = "";
        String returnData = "";
        String apiURL  = "http://localhost:8000/book/bestSeller";
        URL url = new URL(apiURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        StringBuffer  sb = new StringBuffer();
        while ((responseData = br.readLine()) != null) {
            sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
        }
        returnData = sb.toString();
        System.out.println(returnData);
        return responseService.getSingleResult(returnData);
    }



}
