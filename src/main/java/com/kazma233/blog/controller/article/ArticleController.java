package com.kazma233.blog.controller.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.vo.ArticleCategoryVO;
import com.kazma233.blog.entity.article.vo.ArticleFull;
import com.kazma233.blog.entity.article.vo.ArticleQueryVO;
import com.kazma233.blog.entity.article.vo.ArticleSimple;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.article.IArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private IArticleService articleService;

    @GetMapping("")
    public BaseResult articleList(ArticleQueryVO articleQueryVO) {
        articleQueryVO.init();
        PageInfo<ArticleCategoryVO> articlePageInfo = articleService.queryAllPublish(articleQueryVO);

        return BaseResult.success(articlePageInfo);
    }

    @GetMapping("/{id}")
    public BaseResult articleInfo(@PathVariable("id") String id) {
        ArticleFull article = articleService.findFullById(id);

        return BaseResult.success(article);
    }

    @GetMapping("/archive")
    public BaseResult archive() {
        List<ArticleSimple> articleSimples = articleService.queryAllSimple();

        Map<String, List<ArticleSimple>> finallyResult = new LinkedHashMap<>();
        articleSimples.forEach(articleSimple -> {
            List<ArticleSimple> ass = finallyResult.computeIfAbsent(
                    articleSimple.getArchiveDate(),
                    k -> new ArrayList<>()
            );
            ass.add(articleSimple);
        });

        return BaseResult.success(finallyResult);
    }

}
