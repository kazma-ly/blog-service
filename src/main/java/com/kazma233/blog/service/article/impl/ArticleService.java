package com.kazma233.blog.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.cons.CacheNameConstants;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.enums.ArticleStatus;
import com.kazma233.blog.entity.article.exception.ArticleException;
import com.kazma233.blog.entity.article.vo.*;
import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.utils.ShiroUtils;
import com.kazma233.common.HttpUtils;
import com.kazma233.common.Utils;
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
        PageHelper.startPage(articleBackendQuery.getPageNo(), articleBackendQuery.getPageSize());
        List<ArticleCategoryBackendVO> articles = articleDao.queryArticle(articleBackendQuery);

        return new PageInfo<>(articles);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME,
            key = "#root.args[0].toString()")
    @Override
    public PageInfo allPublish(ArticleQuery articleQuery) {
        PageHelper.startPage(articleQuery.getPageNo(), articleQuery.getPageSize());
        List<ArticleCategoryVO> articles = articleDao.queryPublishArticle(articleQuery);

        return new PageInfo<>(articles);
    }

    @Override
    public Article findById(String id) {
        return articleDao.findById(id);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_CACHE_KEY_NAME, key = "#root.args[0]")
    @Override
    public Article findAndContentById(String id) {
        this.updateViewNum(id, 1);

        return articleDao.selectAndContentById(id);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, allEntries = true)
    @Override
    public void saveArticleByURL(ArticleGitAdd articleGitAdd) {
        LocalDateTime now = LocalDateTime.now();

        Article article = Article.builder().
                id(Utils.generateID()).
                title(articleGitAdd.getTitle()).
                subtitle(articleGitAdd.getSubtitle()).
                createTime(now).
                updateTime(now).
                readNum(0).
                archiveDate(now.format(DefaultConstant.DATE_FORMATTER_YM)).
                state(articleGitAdd.getState()).
                categoryId(articleGitAdd.getCategoryId()).
                tags(articleGitAdd.getTags()).
                content(getContentByURL(articleGitAdd.getUrl())).
                uid(ShiroUtils.getUid()).
                build();

        articleDao.insert(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_CACHE_KEY_NAME,
            key = "#root.args[0].id")
    @Override
    public void update(ArticleGitUpdate articleGitUpdate) {

        Article article = Article.builder().
                id(Utils.generateID()).
                title(articleGitUpdate.getTitle()).
                subtitle(articleGitUpdate.getSubtitle()).
                updateTime(LocalDateTime.now()).
                readNum(0).
                state(articleGitUpdate.getState()).
                categoryId(articleGitUpdate.getCategoryId()).
                tags(articleGitUpdate.getTags()).
                content(getContentByURL(articleGitUpdate.getUrl())).
                uid(ShiroUtils.getUid()).
                build();

        articleDao.updateByIdSelective(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, allEntries = true)
    @Override
    public void delete(String id) {
        articleDao.deleteById(id);
    }

    @Override
    public void updateViewNum(String articleId, Integer num) {
        Article exitArticle = articleDao.findById(articleId);

        Article article = Article.builder().
                readNum(exitArticle.getReadNum() + num).
                id(articleId).
                build();

        articleDao.updateByIdSelective(article);
    }

    @Cacheable(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_KEY_NAME, key = CacheNameConstants.SIMPLE_ARTICLE_LIST_CACHE)
    @Override
    public List<ArticleSimple> queryAllSimple() {
        return articleDao.queryArticleSimpleByStatus(ArticleStatus.ENABLE.getCode());
    }

    private static String getContentByURL(String url) {
        String content = HttpUtils.getContentByURL(url);
        if (content.isEmpty()) {
            throw new ArticleException(Status.ARTICLE_EMPTY);
        }

        return content;
    }

}
