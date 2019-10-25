package com.kazma233.blog.service.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.vo.*;

import java.util.List;

public interface IArticleService {

    PageInfo all(ArticleBackendQuery articleBackendQuery);

    PageInfo allPublish(ArticleQuery articleQuery);

    Article findById(String id);

    Article findAndContentById(String id);

    void saveArticleByURL(ArticleGitAdd articleGitAdd);

    void update(ArticleGitUpdate articleGitUpdate);

    void delete(String id);

    void updateViewNum(String articleId, Long num);

    List<ArticleSimple> queryAllSimple();
}
