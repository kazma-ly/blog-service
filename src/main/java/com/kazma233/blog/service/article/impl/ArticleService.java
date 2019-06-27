package com.kazma233.blog.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.enums.article.ArticleStatus;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;
import com.kazma233.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    // cache pre
    public static final String ARTICLE_LIST_PRE = "ARTICLE_LIST_";
    public static final String ARTICLE_PRE = "ARTICLE_";
    public static final String SIMPLE_ARTICLE_LIST_CACHE = "SIMPLE_ARTICLE_LIST_CACHE";

    @Override
    public PageInfo<ArticleCategoryVO> queryAll(ArticleQueryVO articleQueryVO) {
        PageHelper.startPage(articleQueryVO.getPage(), articleQueryVO.getCount());
        List<ArticleCategoryVO> articles = articleDao.queryArticleByArgs(articleQueryVO);

        return new PageInfo<>(articles);
    }

    @Cacheable(key = "#root.target.ARTICLE_LIST_PRE+#articleQueryVO.toString()",
            cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_NAME)
    @Override
    public PageInfo<ArticleCategoryVO> queryAllPublish(ArticleQueryVO articleQueryVO) {
        articleQueryVO.setTextState(ArticleStatus.ENABLE.getCode());

        PageHelper.startPage(articleQueryVO.getPage(), articleQueryVO.getCount());
        List<ArticleCategoryVO> articles = articleDao.queryArticleByArgs(articleQueryVO);

        return new PageInfo<>(articles);
    }

    @Override
    public Article findById(String id) {
        return articleDao.selectById(id);
    }

    @Cacheable(key = "#root.target.ARTICLE_PRE+#id", cacheNames = DefaultConstant.ARTICLE_CACHE_NAME)
    @Override
    public ArticleFull findFullById(String id) {
        return articleDao.selectFullById(id);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_NAME, allEntries = true)
    @Override
    public int save(ArticleFull article) {
        LocalDateTime now = LocalDateTime.now();

        article.setId(Utils.generateID());
        article.setArchiveDate(now.format(DefaultConstant.DATE_FORMATTER_YM));
        article.setReadNum(0);
        article.setCreateTime(now);
        article.setUpdateTime(now);

        return articleDao.insert(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_CACHE_NAME, key = "#root.target.ARTICLE_PRE+#article.id")
    @Override
    public int updateFull(ArticleFull article) {
        article.setUpdateTime(LocalDateTime.now());
        return articleDao.updateByIdSelective(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_NAME, allEntries = true)
    @Override
    public int delete(String id) {
        // 单个文章缓存就不清除了
        return articleDao.deleteById(id);
    }

    @Override
    public int updateViewNum(String articleId, Integer num) {
        Article exitArticle = articleDao.selectById(articleId);

        ArticleFull article = new ArticleFull();
        article.setReadNum(exitArticle.getReadNum() + num);
        article.setId(articleId);

        return articleDao.updateByIdSelective(article);
    }

    @Cacheable(key = "#root.target.SIMPLE_ARTICLE_LIST_CACHE", cacheNames = DefaultConstant.ARTICLE_LIST_CACHE_NAME)
    @Override
    public List<ArticleSimple> queryAllSimple() {
        return articleDao.queryArticleSimple(ArticleStatus.ENABLE.getCode());
    }

}
