package com.kazma233.blog.service.article.impl;

import com.kazma233.blog.dao.article.ArticleCategoryDao;
import com.kazma233.blog.entity.category.ArticleCategory;
import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.article.exception.ArticleException;
import com.kazma233.blog.service.article.IArticleCategoryService;
import com.kazma233.blog.utils.ShiroUtils;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class ArticleCategoryService implements IArticleCategoryService {

    private ArticleCategoryDao articleCategoryDao;

    @Override
    public List<ArticleCategory> all() {
        return articleCategoryDao.findAll(ShiroUtils.getUid());
    }

    @Override
    public void insertCategory(ArticleCategory category) {
        category.setId(Utils.generateID());
        category.setCreateTime(new Date());
        category.setUid(ShiroUtils.getUid());

        articleCategoryDao.insert(category);
    }

    @Override
    public void updateCategory(ArticleCategory category) {
        articleCategoryDao.updateById(category);
    }

    @Override
    public void deleteCategory(String id) {
        Integer count = articleCategoryDao.countByArticleId(id);
        if (count != null && count > 0) {
            throw new ArticleException(Status.CATEGORY_ALREADY_IN_USE);
        }

        articleCategoryDao.deleteById(id);
    }

}
