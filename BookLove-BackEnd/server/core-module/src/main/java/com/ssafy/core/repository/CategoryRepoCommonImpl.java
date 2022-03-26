package com.ssafy.core.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.Category;
import com.ssafy.core.entity.QCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class CategoryRepoCommonImpl implements CategoryRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public CategoryRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }
    @Override
    public Category findCategoryByName(String name) {
        Category result = queryFactory
                .select(QCategory.category)
                .from(QCategory.category)
                .where(QCategory.category.name.eq(name))
                .fetchFirst();
        return result;
    }
}
