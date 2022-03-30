package com.ssafy.api.service;


import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.PyBooksResDTO;
import com.ssafy.core.entity.Book;
import com.ssafy.core.exception.ApiMessageException;
import com.ssafy.core.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

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
                .uri("http://localhost:8000/recommend/" + bookId)
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
    public List<Book> findMainBestseller(){
        List<Book> bestseller = bookRepository.findMainBestseller();

        return bestseller;

    }

    @Transactional(readOnly = false)
    public List<Book> findNewBook(){
        List<Book> newBook = bookRepository.findNewBook();

        return newBook;

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


}
