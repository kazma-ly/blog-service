package com.kazma233.blog.service.article.impl;

import com.kazma233.blog.config.scheduling.ArticleReadNumberUpdateTask;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.enums.ArticleStatus;
import com.kazma233.blog.entity.article.vo.*;
import com.kazma233.blog.entity.common.PageInfo;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.utils.UserUtils;
import com.kazma233.blog.utils.id.IDGenerater;
import com.kazma233.blog.utils.pageable.PageableUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private ArticleDao articleDao;

    @Override
    public PageInfo all(ArticleBackendQuery articleBackendQuery) {
        articleBackendQuery.setUid(UserUtils.getUserId());

        List<ArticleCategoryBackendVO> articles = articleDao.queryArticle(articleBackendQuery);
        Long total = articleDao.queryArticleTotal(articleBackendQuery);

        return PageableUtils.pageInfo(total, articleBackendQuery, articles);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME,
            key = "#root.args[0].toString()")
    @Override
    public PageInfo allPublish(ArticleQuery articleQuery) {
        List<ArticleCategoryVO> articles = articleDao.queryPublishArticle(articleQuery);
        Long total = articleDao.queryPublishArticleSize(articleQuery);

        return PageableUtils.pageInfo(total, articleQuery, articles);
    }

    @Override
    public Article findById(String id) {
        return articleDao.findById(id);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_CACHE_KEY_NAME, key = "#root.args[0]")
    @Override
    public Article findAndContentById(String id) {
        ArticleReadNumberUpdateTask.ReadNumberHelper.addOnce(id);

        return articleDao.selectAndContentById(id);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, allEntries = true)
    @Override
    public void saveArticleByURL(ArticleAdd articleAdd) {
        LocalDateTime now = LocalDateTime.now();

        Article article = Article.builder().
                id(IDGenerater.generateID()).
                title(articleAdd.getTitle()).
                subtitle(articleAdd.getSubtitle()).
                createTime(now).
                updateTime(now).
                readNum(0L).
                archiveDate(now.format(DefaultConstant.DATE_FORMATTER_YM)).
                state(articleAdd.getState()).
                categoryId(articleAdd.getCategoryId()).
                tags(articleAdd.getTags()).
                content(articleAdd.getContent()).
                uid(UserUtils.getUserId()).
                build();

        articleDao.insert(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_CACHE_KEY_NAME,
            key = "#root.args[0].id")
    @Override
    public void update(ArticleUpdate articleUpdate) {

        Article article = Article.builder().
                id(articleUpdate.getId()).
                title(articleUpdate.getTitle()).
                subtitle(articleUpdate.getSubtitle()).
                updateTime(LocalDateTime.now()).
                state(articleUpdate.getState()).
                categoryId(articleUpdate.getCategoryId()).
                tags(articleUpdate.getTags()).
                content(articleUpdate.getContent()).
                uid(UserUtils.getUserId()).
                build();

        articleDao.updateByIdSelective(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, allEntries = true)
    @Override
    public void delete(String id) {
        articleDao.deleteById(id);
    }

    @Override
    public void updateViewNum(String articleId, Long num) {
        Article exitArticle = articleDao.findById(articleId);

        Article article = Article.builder().
                readNum(exitArticle.getReadNum() + num).
                id(articleId).
                build();

        articleDao.updateByIdSelective(article);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, key = "'SIMPLE_ARTICLE_LIST_CACHE'")
    @Override
    public List<ArticleSimple> queryAllSimple() {
        return articleDao.queryArticleSimpleByStatus(ArticleStatus.ENABLE.getCode());
    }

}
