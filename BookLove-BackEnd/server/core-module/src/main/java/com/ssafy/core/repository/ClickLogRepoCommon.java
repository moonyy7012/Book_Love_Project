package com.ssafy.core.repository;

import com.ssafy.core.entity.Book;
import com.ssafy.core.entity.Category;
import com.ssafy.core.entity.ClickLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickLogRepoCommon {
    ClickLog findClickLogByUserIdAndBookId(long userId, long bookId);
    List<Book> findBookByClickLog(String gender, int age);
    Long getUserBookClickCnt(Long userId);
}
