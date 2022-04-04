package com.ssafy.api.service;


import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.PyBooksResDTO;
import com.ssafy.core.entity.Book;
import com.ssafy.core.entity.Category;
import com.ssafy.core.entity.ClickLog;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.ApiMessageException;
import com.ssafy.core.repository.BookRepository;
import com.ssafy.core.repository.ClickLogRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final ClickLogRepository clickLogRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public List<Book> findBestseller(String categoryName){
        List<Book> bestseller = bookRepository.findBestsellerByCategoryName(categoryName);

        return bestseller;

    }

    @Transactional(readOnly = false)
    public Book findBook(long bookId){
        Book book = bookRepository.findBookInfoByBookId(bookId);

        if (book == null) {
            throw new ApiMessageException("해당하는 책이 없습니다.");
        }

        return book;
    }

    public List<BookListInfoResDTO> findSimilarBooks(long bookId) {
        List<PyBooksResDTO> similarBooks = WebClient.create().get()
                .uri("http://localhost:8000/recommend/book/" + bookId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ApiMessageException("내부 서버 에러")))
                .bodyToFlux(PyBooksResDTO.class)
                .toStream()
                .collect(Collectors.toList());

        List<BookListInfoResDTO> resultList = IntStream.range(0, similarBooks.size())
                .mapToObj(i -> BookListInfoResDTO.builder()
                        .title(similarBooks.get(i).getTitle())
                        .cover(similarBooks.get(i).getCover())
                        .bookId(similarBooks.get(i).getBook_id()).build())
                .collect(Collectors.toList());

        return resultList;
    }

    @Transactional(readOnly = false)
    public List<BookListInfoResDTO> findMainBestseller(){
        List<Book> bestseller = bookRepository.findMainBestseller();

        List<BookListInfoResDTO> infoLIst = new ArrayList<>();
        for(int i = 0 ; i < bestseller.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(bestseller.get(i).getTitle())
                    .cover(bestseller.get(i).getCover())
                    .bookId(bestseller.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return infoLIst;

    }

    @Transactional(readOnly = false)
    public List<BookListInfoResDTO> findNewBook(){
        List<Book> newBook = bookRepository.findNewBook();

        List<BookListInfoResDTO> infoLIst = new ArrayList<>();
        for(int i = 0 ; i < newBook.size() ; i++) {
            BookListInfoResDTO info = BookListInfoResDTO.builder()
                    .title(newBook.get(i).getTitle())
                    .cover(newBook.get(i).getCover())
                    .bookId(newBook.get(i).getBookId())
                    .build();
            infoLIst.add(info);
        }

        return infoLIst;
    }

    @Transactional(readOnly = false)
    public List<Book> findBookByTitle(String Title){
        List<Book> titleList = bookRepository.findBookByTitle(Title);

        return titleList;

    }

    @Transactional(readOnly = false)
    public List<Book> findBookByAuthor(String author){
        List<Book> titleList = bookRepository.findBookByAuthor(author);

        return titleList;

    }

    @Transactional(readOnly = false)
    public ClickLog putClickLog(Long userId, Book book) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiMessageException("존재하지 않는 회원정보입니다."));
        ClickLog clickLog = clickLogRepository.findClickLogByUserIdAndBookId(user.getUserId(), book.getBookId());

        if (clickLog == null) {
            clickLog = ClickLog.builder()
                    .user(user)
                    .book(book)
                    .count(1)
                    .build();
        } else {
            clickLog.addCount();
        }

        return clickLogRepository.save(clickLog);
    }

    public List<BookListInfoResDTO> findBookByGenderAndAgeClickLog(String gender, int age) {
       List<Book> bookList = clickLogRepository.findBookByClickLog(gender, age);

        List<BookListInfoResDTO> resultList = IntStream.range(0, bookList.size())
                .mapToObj(i -> BookListInfoResDTO.builder()
                        .title(bookList.get(i).getTitle())
                        .cover(bookList.get(i).getCover())
                        .bookId(bookList.get(i).getBookId()).build())
                .collect(Collectors.toList());

        return resultList;
    }

    @Transactional(readOnly = false)
    public List<BookListInfoResDTO> findBestsellerByCategoryList(User user) {
        List<BookListInfoResDTO> resultList;
        List<Book> bestseller;
        Long userClickCnt = clickLogRepository.getUserBookClickCnt(user.getUserId());

        if (userClickCnt >= 10) {
            List<Category> categories = clickLogRepository.findCategoryByClickLog(user.getUserId());
            bestseller = bookRepository.findBestsellerByCategoryList(categories);
        } else {
            bestseller = bookRepository.findBestsellerByCategoryList(user.getCategories());
        }

        resultList = IntStream.range(0, bestseller.size())
                .mapToObj(i -> BookListInfoResDTO.builder()
                        .title(bestseller.get(i).getTitle())
                        .cover(bestseller.get(i).getCover())
                        .bookId(bestseller.get(i).getBookId()).build())
                .collect(Collectors.toList());

        return resultList;
    }
}
