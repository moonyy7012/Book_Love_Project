package com.ssafy.core.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ClickLogRepoCommonImpl implements ClickLogRepoCommon {
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public ClickLogRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public ClickLog findClickLogByUserIdAndBookId(long userId, long bookId) {
        return queryFactory
                .selectFrom(QClickLog.clickLog)
                .where(
                        userEq(userId),
                        bookEq(bookId)
                )
                .fetchOne();
    }

    @Override
    public List<Book> findBookByClickLog(String gender, int age) {
        return queryFactory
                .select(QClickLog.clickLog.book)
                .from(QClickLog.clickLog)
                .leftJoin(QClickLog.clickLog.user)
                .leftJoin(QClickLog.clickLog.book)
                .where(
                        genderEq(gender),
                        ageEq(age)
                )
                .groupBy(QClickLog.clickLog.book.bookId)
                .orderBy(QClickLog.clickLog.book.bookId.count().desc(), QClickLog.clickLog.book.salesPoint.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public Long getUserBookClickCnt(Long userId) {
        return queryFactory
                .select(QClickLog.clickLog.user.userId.count())
                .from(QClickLog.clickLog)
                .where(
                        userEq(userId)
                )
                .fetchOne();
    }

    @Override
    public List<Category> findCategoryByClickLog(Long userId) {
        List<Category> result  = queryFactory
                .select(QClickLog.clickLog.book.category)
                .from(QClickLog.clickLog)
                .leftJoin(QClickLog.clickLog.book)
                .where(
                        userEq(userId)
                )
                .groupBy(QClickLog.clickLog.book.category.categoryId)
                .orderBy(QClickLog.clickLog.book.category.categoryId.count().desc())
                .limit(1)
                .fetch();

        return result;
    }

    private BooleanExpression userEq(Long userId) {
        return userId != null ? QClickLog.clickLog.user.userId.eq(userId) : null;
    }

    private BooleanExpression bookEq(Long bookId) {
        return bookId != null ? QClickLog.clickLog.book.bookId.eq(bookId) : null;
    }

    private BooleanExpression genderEq(String gender) {
        return gender != null ? QClickLog.clickLog.user.gender.eq(gender) : null;
    }

    private BooleanExpression ageEq(Integer age) {
        return age != null ? QClickLog.clickLog.user.age.eq(age) : null;
    }
}
