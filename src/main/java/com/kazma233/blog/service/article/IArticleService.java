package com.kazma233.blog.service.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;

import java.util.List;

public interface IArticleService {

    PageInfo<ArticleCategoryVO> queryAll(ArticleQueryVO articleQueryVO);

    PageInfo<ArticleCategoryVO> queryAllPublish(ArticleQueryVO articleQueryVO);

    Article findById(String id);

    ArticleFull findFullById(String id);

    int save(ArticleFull article);

    int updateFull(ArticleFull article);

    int delete(String id);

    int updateViewNum(String articleId, Integer num);

    List<ArticleSimple> queryAllSimple();
}
