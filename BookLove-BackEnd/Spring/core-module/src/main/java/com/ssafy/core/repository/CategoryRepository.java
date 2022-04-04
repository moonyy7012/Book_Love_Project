package com.ssafy.core.repository;

import com.ssafy.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepoCommon {

}
