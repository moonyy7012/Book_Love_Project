package com.ssafy.core.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Transactional
public class BookRepoCommonImpl implements BookRepoCommon {
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
                .where(QBook.book.category.name.eq(categoryName))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(20)
                .fetch();
        return result;
    }

    @Override
    public List<Book> findMainBestseller() {
        List<Book> result  = queryFactory
                .selectFrom(QBook.book)
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(20)
                .fetch();
        return result;
    }

    @Override
    public List<Book> findNewBook() {
        StringTemplate formattedDate = Expressions.stringTemplate("CONVERT(CHAR(10), {0})",
                QBook.book.pubDate);
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = localDate.format(formatter);


        List<Book> result = queryFactory
                .selectFrom(QBook.book)
                .where(QBook.book.pubDate.loe(formattedString))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.pubDate.desc(), QBook.book.salesPoint.desc())
                .limit(20)
                .fetch();

        return result;
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

    @Override
    public List<Book> findBookByTitle(String title) {

        List<Book> result  = queryFactory
                .selectFrom(QBook.book)
                .where(QBook.book.title.contains(title))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(20)
                .fetch();
        return result;
    }

    @Override
    public List<Book> findBookByAuthor(String author) {

        List<Book> result  = queryFactory
                .selectFrom(QBook.book)
                .where(QBook.book.author.contains(author))
                .leftJoin(QBook.book.category, QCategory.category)
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(20)
                .fetch();
        return result;
    }

    @Override
    public List<Book> findBestsellerByCategoryList(List<Category> categories) {
        List<Book> result  = queryFactory
                .selectFrom(QBook.book)
                .leftJoin(QBook.book.category, QCategory.category)
                .where(QBook.book.category.in(categories))
                .fetchJoin()
                .orderBy(QBook.book.salesPoint.desc())
                .limit(10)
                .fetch();
        return result;
    }

    private BooleanExpression userEq(Long userId) {
        return userId != null ? QClickLog.clickLog.user.userId.eq(userId) : null;
    }
}
