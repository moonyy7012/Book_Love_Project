package com.ssafy.api.service;


import com.ssafy.core.entity.Book;
import com.ssafy.core.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = false)
    public List<Book> findBestseller(String categoryName){
        List<Book> bestseller = bookRepository.findBestSellerByCategoryName(categoryName);

        return bestseller;

    }
    @Transactional(readOnly = false)
    public Book findBook(long bookId){
        Book book = bookRepository.findBookInfoByBookId(bookId);
        return book;
    }


}
