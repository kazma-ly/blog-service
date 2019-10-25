package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import com.kazma233.blog.entity.comment.vo.CommentUpdate;
import com.kazma233.blog.entity.comment.vo.RecentCommentVO;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.article.ICommentService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/comments")
public class CommentManageController {

    private ICommentService commentService;

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @GetMapping
    public BaseResult all(CommentQuery commentQuery) {
        PageInfo<CommentArticleTitleVO> commentArticlePage = commentService.queryAllCommentAndArticleTitle(commentQuery);

        return BaseResult.success(commentArticlePage);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @PatchMapping
    public BaseResult commentStatusUpdate(@RequestBody @Validated CommentUpdate commentUpdate) {
        commentService.updateStatues(commentUpdate);

        return BaseResult.success();
    }

}
