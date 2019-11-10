package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.vo.ArticleBackendQuery;
import com.kazma233.blog.entity.article.vo.ArticleGitAdd;
import com.kazma233.blog.entity.article.vo.ArticleGitUpdate;
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
        PageInfo articlePageInfo = articleService.all(articleQueryVO);

        return BaseResult.success(articlePageInfo);
    }

    @PostMapping
    public BaseResult addArticle(@Validated @RequestBody ArticleGitAdd articleGitAdd) {
       articleService.saveArticleByURL(articleGitAdd);

        return BaseResult.success();
    }

    @PutMapping
    public BaseResult updateArticle(@Validated @RequestBody ArticleGitUpdate articleGitUpdate) {
        articleService.update(articleGitUpdate);

        return BaseResult.success();
    }

    @DeleteMapping(value = "/{id}")
    public BaseResult deleteArticle(@PathVariable("id") String id) {
        articleService.delete(id);

        return BaseResult.success();
    }

}
