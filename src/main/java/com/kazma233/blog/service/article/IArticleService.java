package com.kazma233.blog.service.article;

import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.vo.*;
import com.kazma233.blog.entity.common.PageInfo;

import java.util.List;

public interface IArticleService {

    PageInfo all(ArticleBackendQuery articleBackendQuery);

    PageInfo allPublish(ArticleQuery articleQuery);

    Article findById(String id);

    Article findAndContentById(String id);

    void saveArticleByURL(ArticleAdd articleAdd);

    void update(ArticleUpdate articleUpdate);

    void delete(String id);

    void updateViewNum(String articleId, Long num);

    List<ArticleSimple> queryAllSimple();
}
