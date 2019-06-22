package com.kazma233.blog.controller.manage.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.article.ArticleCategory;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.IArticleCategoryService;
import com.kazma233.blog.utils.ValidatedUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zly
 * @date 2019/1/4
 **/
@RestController
@RequestMapping("/manage")
public class ArticleCategoryManageController {

    @Autowired
    private IArticleCategoryService articleCategoryService;

    /**
     * 查询所有分类
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping("/categorys")
    public BaseResult queryAllCategory() {
        List<ArticleCategory> categories = articleCategoryService.queryAll();
        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, categories);
    }

    /**
     * 新增一个分类
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PostMapping("/category")
    public BaseResult addCategory(@Validated(value = {AddGroup.class}) @RequestBody ArticleCategory category, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        int res = articleCategoryService.insertCategory(category);
        return BaseResult.createResult(res);
    }

    /**
     * 更新
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PutMapping("/category")
    public BaseResult updateCategory(@Validated(value = {UpdateGroup.class}) @RequestBody ArticleCategory category, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        Integer res = articleCategoryService.updateCategory(category);
        return BaseResult.createResult(res);
    }

    /**
     * 删除一个分类
     */
    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping("/category/{id}")
    public BaseResult deleteCategory(@PathVariable String id) {
        Integer res = articleCategoryService.deleteCategory(id);
        return BaseResult.createResult(res);
    }

}
