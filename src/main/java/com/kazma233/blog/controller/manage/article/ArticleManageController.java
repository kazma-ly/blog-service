package com.kazma233.blog.controller.manage.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zly
 * @date 2019/1/4
 **/
@RestController
@RequestMapping("/manage")
public class ArticleManageController {

    @Autowired
    private IArticleService articleService;

    /**
     * 查询所有文章,用于展示管理页面,包括未发布的文章
     *
     * @param articleQueryVO 查询参数
     * @return 返回结果
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping(value = "/articles")
    public BaseResult queryAllText(ArticleQueryVO articleQueryVO) {
        articleQueryVO.init();
        PageInfo<ArticleCategoryVO> articlePageInfo = articleService.queryAllArticle(articleQueryVO);
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, articlePageInfo);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PostMapping(value = "/article")
    public BaseResult submitText(@Validated(value = AddGroup.class) @RequestBody ArticleFull article, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        int res = articleService.saveArticle(article);
        return BaseResult.createResult(res);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PutMapping(value = "/article")
    public BaseResult changeText(@Validated(UpdateGroup.class) @RequestBody ArticleFull article, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        int res = articleService.updateFullArticle(article);
        return BaseResult.createResult(res);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping(value = "/article/{id}")
    public BaseResult deleteText(@PathVariable("id") String id) {
        int res = articleService.deleteArticle(id);
        return BaseResult.createResult(res);
    }

    /**
     * 处理日期
     */
    @GetMapping("/article/adjust")
    public BaseResult adjustArticle() {
        articleService.adjustDate();
        return BaseResult.createResult(BaseResult.SUCCESS);
    }

}
