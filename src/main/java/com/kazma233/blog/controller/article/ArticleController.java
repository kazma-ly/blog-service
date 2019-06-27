package com.kazma233.blog.controller.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 文章Controller
 *
 * @author zly
 * @date 2019/1/4
 **/
@RestController
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping(value = "/articles")
    public BaseResult articleList(ArticleQueryVO articleQueryVO) {
        articleQueryVO.init();
        PageInfo<ArticleCategoryVO> articlePageInfo = articleService.queryAllPublish(articleQueryVO);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, articlePageInfo);
    }

    @GetMapping(value = "/articles/{id}")
    public BaseResult articleInfo(@PathVariable("id") String id) {
        ArticleFull article = articleService.findFullById(id);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, article);
    }

    @GetMapping("/articles/archive")
    public BaseResult archive() {
        List<ArticleSimple> articleSimples = articleService.queryAllSimple();

        Map<String, List<ArticleSimple>> lastResult = new LinkedHashMap<>();
        articleSimples.forEach(articleSimple -> {
            String archiveDate = articleSimple.getArchiveDate();
            List<ArticleSimple> ass = lastResult.computeIfAbsent(archiveDate, k -> new ArrayList<>());
            ass.add(articleSimple);
        });

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, lastResult);
    }

}
