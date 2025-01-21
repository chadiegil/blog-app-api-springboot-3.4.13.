package com.whitecat.blog.services;

import com.whitecat.blog.domain.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();
}
