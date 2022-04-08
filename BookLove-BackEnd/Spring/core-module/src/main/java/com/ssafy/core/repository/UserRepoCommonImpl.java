package com.ssafy.core.repository;

import com.ssafy.core.entity.*;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.code.YNCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public UserRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }


    @Override
    @Transactional
    public User findUserLogin(String id, JoinCode type){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.id.eq(id), QUser.user.type.eq(type))
                .leftJoin(QUser.user.categories, QCategory.category)
                .fetchJoin()
                .fetchFirst();

        return result;
    }

    @Override
    @Transactional
    public User findUserWithCategory(Long userId){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.userId.eq(userId))
                .leftJoin(QUser.user.categories, QCategory.category)
                .fetchJoin()
                .fetchFirst();

        return result;
    }


    @Override
    public User findById(String id){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.id.eq(id))
                .fetchOne();
        return result;
    }





}





