package com.ssafy.api.controller;


import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    BookService bookService;

    @Test
    public void 추천도서() throws Exception {
        //given
        long bookId = 1;

        //when
        List<BookListInfoResDTO> result = bookService.findSimilarBooks(bookId);

        //then
        //System.out.println(result.get(1).getTitle());
        assertNotNull(result);
    }
}
