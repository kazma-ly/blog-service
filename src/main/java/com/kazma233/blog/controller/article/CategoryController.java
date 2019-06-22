package com.kazma233.blog.controller.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.article.ArticleCategory;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.IArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zly
 * @date 2019/1/3
 **/
@RestController
public class CategoryController {

    @Autowired
    private IArticleCategoryService articleCategoryService;

    /**
     * 查询开启的标签分类
     */
    @GetMapping("/categorys")
    public BaseResult queryAllCategory() {
        List<ArticleCategory> categories = articleCategoryService.queryAll();

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, categories);
    }


}
