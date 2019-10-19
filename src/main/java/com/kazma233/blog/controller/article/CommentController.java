package com.kazma233.blog.controller.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.comment.vo.CommentAdd;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import com.kazma233.blog.entity.result.BaseResult;
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
        commentService.insert(comment);

        return BaseResult.success();
    }

    @GetMapping
    public BaseResult<PageInfo> articleComment(CommentQuery commentQueryVO) {
        PageInfo pageInfo = commentService.queryArticleComment(commentQueryVO);

        return BaseResult.success(pageInfo);
    }

}
