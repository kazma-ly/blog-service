package com.kazma233.blog.service.article.impl;

import com.kazma233.blog.dao.article.ArticleCategoryDao;
import com.kazma233.blog.entity.article.ArticleCategory;
import com.kazma233.blog.exception.ArticleException;
import com.kazma233.blog.service.article.IArticleCategoryService;
import com.kazma233.common.SecretTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleCategoryService implements IArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Override
    public List<ArticleCategory> queryAll() {
        return articleCategoryDao.queryAll();
    }

    @Override
    public int insertCategory(ArticleCategory category) {

        String id = SecretTool.getInstance().generateValue();
        category.setId(id);
        category.setCreateTime(new Date());

        return articleCategoryDao.insert(category);
    }

    @Override
    public int updateCategory(ArticleCategory category) {
        return articleCategoryDao.updateById(category);
    }

    @Override
    public int deleteCategory(String id) {
        int count = articleCategoryDao.queryCountByArticleId(id);
        if (count > 0) {
            throw new ArticleException("该分类还在使用，不能删除");
        }
        return articleCategoryDao.deleteById(id);
    }

}
