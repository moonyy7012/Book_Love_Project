package com.ssafy.core.repository;


import com.ssafy.core.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepoCommon {

    Category findCategoryByName(String name);
}
