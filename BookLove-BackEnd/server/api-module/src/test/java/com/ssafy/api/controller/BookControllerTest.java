//package com.ssafy.api.controller;
//
//import com.ssafy.api.dto.res.BookListInfoResDTO;
//import com.ssafy.api.service.BookService;
//import com.ssafy.core.entity.Book;
//import com.ssafy.core.entity.Category;
//import com.ssafy.core.entity.ClickLog;
//import com.ssafy.core.entity.User;
//import org.junit.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//@AutoConfigureMockMvc
//public class BookControllerTest {
//
//    @Autowired
//    BookService bookService;
//
//    @DisplayName("유사 도서")
//    @Test
//    public void recommendTest() throws Exception {
//        //given
//        long bookId = 1;
//
//        //when
//        List<BookListInfoResDTO> result = bookService.findSimilarBooks(bookId);
//
//        //then
//        assertNotNull(result);
//    }
//
////    @DisplayName("클릭 로그 X")
////    @Test
////    public void clickTest() throws Exception {
////        //given
////        User user  = User.builder().userId(0).gender("여").age(10).nickname("부끄럽").build();
////        Book book = Book.builder().bookId(0).category(Category.builder().categoryId(10).name("카테고리1").build()).build();
////
////        u
////
////        ClickLog clickLog = ClickLog.builder().book(book).user(user).build();
////
////        //when
////        ClickLog result = bookService.putClickLog(user, book);
////
////        //then
////        assertTrue(result.getCount() == 1);
////    }
//}
