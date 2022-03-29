package com.ssafy.core.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.Book;
import com.ssafy.core.entity.QBook;
import com.ssafy.core.entity.QCategory;
import com.ssafy.core.entity.QUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookRepoCommonImpl implements BookRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public BookRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }


    @Override
    public List<Book> findBestsellerByCategoryName(String categoryName) {
        List<Book> result  = queryFactory
                .selectFrom(QBook.book)
                .where(QBook.book.category_name.contains(categoryName))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(10)
                .fetch();

        return result;
    }

    @Override
    public List<Book> findNewBookByCategoryName(String categoryName) {


        return null;
    }

    @Override
    public Book findBookInfoByBookId(long bookId) {
        Book bookInfo = queryFactory
                .select(QBook.book)
                .from(QBook.book)
                .where(QBook.book.bookId.eq(bookId))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .fetchFirst();
        return bookInfo;
    }
}
