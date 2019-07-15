package com.kazma233.blog.service.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.vo.ArticleCategoryVO;
import com.kazma233.blog.entity.article.vo.ArticleFull;
import com.kazma233.blog.entity.article.vo.ArticleQueryVO;
import com.kazma233.blog.entity.article.vo.ArticleSimple;

import java.util.List;

public interface IArticleService {

    PageInfo<ArticleCategoryVO> queryAll(ArticleQueryVO articleQueryVO);

    PageInfo<ArticleCategoryVO> queryAllPublish(ArticleQueryVO articleQueryVO);

    Article findById(String id);

    ArticleFull findFullById(String id);

    void save(ArticleFull article);

    void updateFull(ArticleFull article);

    void delete(String id);

    void updateViewNum(String articleId, Integer num);

    List<ArticleSimple> queryAllSimple();
}
