package com.ssafy.core.repository;


import com.ssafy.core.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepoCommon {
    List<Book> findBestsellerByCategoryName(String categoryName);
    List<Book> findMainBestseller();
    List<Book> findNewBook();
    Book findBookInfoByBookId(long bookId);
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(String author);
}
