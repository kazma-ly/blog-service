package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage")
public class CommentManageController {

    @Autowired
    private ICommentService commentService;

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping(value = "/comments")
    public BaseResult all(@Validated CommentQueryVO commentQueryVO) {
        commentQueryVO.init();
        PageInfo<CommentAndArticleVO> pageInfo = commentService.queryAllCommentAndArticleTitle(commentQueryVO);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping(value = "/comment/{id}")
    public BaseResult deleteComment(@PathVariable("id") String id) {
        int res = commentService.deleteById(id);
        return BaseResult.createResult(res);
    }

}
