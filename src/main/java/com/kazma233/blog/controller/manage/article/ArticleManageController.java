package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.vo.ArticleCategoryVO;
import com.kazma233.blog.entity.article.vo.ArticleFull;
import com.kazma233.blog.entity.article.vo.ArticleQueryVO;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.article.IArticleService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/articles")
public class ArticleManageController {

    private IArticleService articleService;

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping(value = "")
    public BaseResult queryAllText(ArticleQueryVO articleQueryVO) {
        articleQueryVO.init();
        PageInfo<ArticleCategoryVO> articlePageInfo = articleService.queryAll(articleQueryVO);

        return BaseResult.success(articlePageInfo);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PostMapping(value = "")
    public BaseResult submitText(@Validated(value = AddGroup.class) @RequestBody ArticleFull article) {
       articleService.save(article);

        return BaseResult.success();
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PutMapping(value = "")
    public BaseResult changeText(@Validated(UpdateGroup.class) @RequestBody ArticleFull article) {
        articleService.updateFull(article);

        return BaseResult.success();
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping(value = "/{id}")
    public BaseResult deleteText(@PathVariable("id") String id) {
        articleService.delete(id);

        return BaseResult.success();
    }

}
