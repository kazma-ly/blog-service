package com.kazma233.blog.service.article;

import com.kazma233.blog.entity.category.ArticleCategory;

import java.util.List;

public interface IArticleCategoryService {

    List<ArticleCategory> all();

    void insertCategory(ArticleCategory category);

    void updateCategory(ArticleCategory category);

    void deleteCategory(String id);
}
