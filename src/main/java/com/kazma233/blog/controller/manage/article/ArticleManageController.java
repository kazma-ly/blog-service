package com.kazma233.blog.controller.manage.article;

import com.kazma233.blog.entity.article.vo.ArticleAdd;
import com.kazma233.blog.entity.article.vo.ArticleBackendQuery;
import com.kazma233.blog.entity.article.vo.ArticleUpdate;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.article.IArticleService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/articles")
public class ArticleManageController {

    private IArticleService articleService;

    @GetMapping
    public BaseResult queryAllArticle(ArticleBackendQuery articleQueryVO) {
        return BaseResult.success(articleService.all(articleQueryVO));
    }

    @PostMapping
    public BaseResult addArticle(@Validated @RequestBody ArticleAdd articleAdd) {
        articleService.saveArticleByURL(articleAdd);

        return BaseResult.success();
    }

    @PutMapping
    public BaseResult updateArticle(@Validated @RequestBody ArticleUpdate articleUpdate) {
        articleService.update(articleUpdate);

        return BaseResult.success();
    }

    @DeleteMapping(value = "/{id}")
    public BaseResult deleteArticle(@PathVariable("id") String id) {
        articleService.delete(id);

        return BaseResult.success();
    }

}
