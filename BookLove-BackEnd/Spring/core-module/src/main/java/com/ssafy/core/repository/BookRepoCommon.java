package com.ssafy.core.repository;


import com.ssafy.core.entity.Book;
import com.ssafy.core.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepoCommon {
    List<Book> findBestsellerByCategoryName(String categoryName);
    List<Book> findBestsellerByCategoryList(List<Category> categories);
    List<Book> findMainBestseller();
    List<Book> findNewBook();
    Book findBookInfoByBookId(long bookId);
    List<Book> findBookByTitle(String title);
    List<Book> findBookByAuthor(String author);
}
