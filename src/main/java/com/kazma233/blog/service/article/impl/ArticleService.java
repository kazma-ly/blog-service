package com.kazma233.blog.service.article.impl;

import com.kazma233.blog.cons.DefaultConstant;
import com.kazma233.blog.dao.article.ArticleDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.enums.ArticleStatus;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;
import com.kazma233.common.SecretTool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageInfo<ArticleCategoryVO> queryAllArticle(ArticleQueryVO articleQueryVO) {
        PageHelper.startPage(articleQueryVO.getPage(), articleQueryVO.getCount());
        List<ArticleCategoryVO> articles = articleDao.queryArticleByArgs(articleQueryVO);

        return new PageInfo<>(articles);
    }

    @Cacheable(key = "'ARTICLE_LIST'+#articleQueryVO.categoryId+'_'+#articleQueryVO.page+'_'+#articleQueryVO.count", cacheNames = DefaultConstant.ARTICLE_CACHE_NAME)
    @Override
    public PageInfo<ArticleCategoryVO> queryPublishArticle(ArticleQueryVO articleQueryVO) {
        articleQueryVO.setTextState(ArticleStatus.ENABLE.getCode());
        PageHelper.startPage(articleQueryVO.getPage(), articleQueryVO.getCount());
        List<ArticleCategoryVO> articles = articleDao.queryArticleByArgs(articleQueryVO);

        return new PageInfo<>(articles);
    }

    @Override
    public Article findById(String id) {
        return articleDao.selectById(id);
    }

    @Cacheable(key = "'FULL_ARTICLE_'+#id", cacheNames = DefaultConstant.ARTICLE_ONE)
    @Override
    public ArticleFull findFullById(String id) {
        return articleDao.selectFullById(id);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_CACHE_NAME, allEntries = true)
    @Override
    public int saveArticle(ArticleFull article) {
        String id = SecretTool.getInstance().generateValue();

        LocalDateTime now = LocalDateTime.now();

        article.setId(id);
        article.setArchiveDate(now.format(DefaultConstant.DATE_FORMATTER_YM));
        article.setReadNum(0);
        article.setCreateTime(now);
        article.setUpdateTime(new Date());

        return articleDao.insert(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_ONE, key = "'FULL_ARTICLE_'+#article.id")
    @Override
    public int updateFullArticle(ArticleFull article) {
        article.setUpdateTime(new Date());
        return articleDao.updateByIdSelective(article);
    }

    @CacheEvict(cacheNames = DefaultConstant.ARTICLE_CACHE_NAME, allEntries = true)
    @Override
    public int deleteArticle(String id) {
        // 单个文章缓存就不清除了
        return articleDao.deleteById(id);
    }

    @Override
    public int updateTextReadNum(String articleId, Integer num) {
        Article exitArticle = articleDao.selectById(articleId);

        ArticleFull article = new ArticleFull();
        article.setReadNum(exitArticle.getReadNum() + num);
        article.setId(articleId);

        return articleDao.updateByIdSelective(article);
    }

    @Cacheable(key = "'ALL_SIMPLE_ARTICLE_LIST'", cacheNames = DefaultConstant.ARTICLE_CACHE_NAME)
    @Override
    public List<ArticleSimple> querySimpleArticleList() {
        return articleDao.queryArticleSimple(ArticleStatus.ENABLE.getCode());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adjustDate() {
        ArticleQueryVO articleQueryVO = new ArticleQueryVO();
        articleQueryVO.setPage(1);
        articleQueryVO.setCount(100);

        PageInfo<ArticleCategoryVO> articleCategoryVOPageInfo = this.queryAllArticle(articleQueryVO);
        List<ArticleCategoryVO> list = articleCategoryVOPageInfo.getList();
        list.forEach(articleCategoryVO -> {
            ArticleFull articleFull = new ArticleFull();
            articleFull.setId(articleCategoryVO.getId());
            articleFull.setArchiveDate(articleCategoryVO.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM")));
            this.updateFullArticle(articleFull);
        });
    }

}
