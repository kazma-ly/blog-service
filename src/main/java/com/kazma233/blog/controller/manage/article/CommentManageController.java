package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import com.kazma233.blog.entity.comment.vo.CommentUpdate;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.article.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/comments")
public class CommentManageController {

    private ICommentService commentService;

    @GetMapping
    public BaseResult all(CommentQuery commentQuery) {
        PageInfo<CommentArticleTitleVO> commentArticlePage = commentService.queryAllCommentAndArticleTitle(commentQuery);

        return BaseResult.success(commentArticlePage);
    }

    @PatchMapping
    public BaseResult commentStatusUpdate(@RequestBody @Validated CommentUpdate commentUpdate) {
        commentService.updateStatues(commentUpdate);

        return BaseResult.success();
    }

}
