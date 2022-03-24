package com.ssafy.core.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.QUserCategory;
import com.ssafy.core.entity.UserCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserCategoryRepoCommonImpl implements UserCategoryRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public UserCategoryRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }
    @Override
    public List<UserCategory> getCategoryByUserId(long userId) {
        return queryFactory.selectFrom(QUserCategory.userCategory)
                .where(QUserCategory.userCategory.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public UserCategory findCategoryByCategoryId(long categoryId) {
        return queryFactory.selectFrom(QUserCategory.userCategory)
                .where(QUserCategory.userCategory.userCategoryId.eq(categoryId))
                .fetchOne();
    }

}
