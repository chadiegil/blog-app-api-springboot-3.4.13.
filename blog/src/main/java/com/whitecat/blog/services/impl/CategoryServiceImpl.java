package com.whitecat.blog.services.impl;

import com.whitecat.blog.domain.entities.Category;
import com.whitecat.blog.repositories.CategoryRepository;
import com.whitecat.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }
}
