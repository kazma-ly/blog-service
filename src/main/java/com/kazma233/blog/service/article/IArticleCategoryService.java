package com.kazma233.blog.service.article;

import com.kazma233.blog.entity.article.ArticleCategory;

import java.util.List;

/**
 * @author zly
 * @date 2019/1/4
 **/
public interface IArticleCategoryService {

    List<ArticleCategory> queryAll();

    int insertCategory(ArticleCategory category);

    int updateCategory(ArticleCategory category);

    int deleteCategory(String id);
}
