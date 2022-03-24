package com.ssafy.core.repository;


import com.ssafy.core.entity.UserCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepoCommon {
    List<UserCategory> getCategoryByUserId(long userId);

    UserCategory findCategoryByCategoryId(long categoryId);

}
