package com.ssafy.core.repository;


import com.ssafy.core.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepoCommon {
    List<Book> findBestsellerByCategoryName(String categoryName);
    List<Book> findNewBookByCategoryName(String categoryName);
    Book findBookInfoByBookId(long bookId);
}
