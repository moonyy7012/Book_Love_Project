package com.ssafy.api.service;


import com.ssafy.core.entity.User;
import com.ssafy.core.entity.UserCategory;
import com.ssafy.core.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final UserCategoryRepository userCategoryRepository;

    @Transactional(readOnly = false)
    public List<UserCategory> getUserCategoryByUserId(long userId){
        return userCategoryRepository.getCategoryByUserId(userId);
    }

    @Transactional(readOnly = false)
    public long enrollCategory(UserCategory userCategory){
        UserCategory enrollCategory = userCategoryRepository.save(userCategory);
        return enrollCategory.getUserCategoryId();
    }

    @Transactional(readOnly = false)
    public void deleteCategory(UserCategory userCategory){
        userCategoryRepository.delete(userCategory);
    }

    @Transactional(readOnly = false)
    public UserCategory findCategoryByCategoryId(long userCategoryId){
        return userCategoryRepository.findCategoryByCategoryId(userCategoryId);
    }

}
