package com.kazma233.blog.controller.article;

import com.kazma233.blog.entity.comment.vo.CommentAdd;
import com.kazma233.blog.entity.comment.vo.CommentArticleQuery;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.article.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private ICommentService commentService;

    @PostMapping
    public BaseResult commit(@RequestBody @Validated CommentAdd comment) {
        commentService.commit(comment);

        return BaseResult.success();
    }

    @GetMapping
    public BaseResult articleComment(CommentArticleQuery commentArticleQuery) {
        return BaseResult.success(commentService.queryArticleComment(commentArticleQuery));
    }

}
