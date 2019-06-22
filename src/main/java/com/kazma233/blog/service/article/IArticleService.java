package com.kazma233.blog.service.article;

import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author zly
 * @date 2019/1/4
 **/
public interface IArticleService {

    /**
     * 查询所有的文章
     */
    PageInfo<ArticleCategoryVO> queryAllArticle(ArticleQueryVO articleQueryVO);

    /**
     * 查询发布的文章
     */
    PageInfo<ArticleCategoryVO> queryPublishArticle(ArticleQueryVO articleQueryVO);

    /**
     * 查询文章
     */
    Article findById(String id);

    /**
     * 查询完整的文章
     *
     * @param id 文章id
     */
    ArticleFull findFullById(String id);

    /**
     * 保存文章
     *
     * @param article 文章信息
     */
    int saveArticle(ArticleFull article);

    /**
     * 更新文章
     */
    int updateFullArticle(ArticleFull article);

    /**
     * 删除文章
     */
    int deleteArticle(String id);

    /**
     * 新增文章阅读量
     */
    int updateTextReadNum(String articleId, Integer num);

    /**
     * 查询所有 只查询归档的信息
     */
    List<ArticleSimple> querySimpleArticleList();

    /**
     * 调整文章归档日期
     */
    void adjustDate();
}
