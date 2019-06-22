package com.kazma233.blog.controller.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import com.kazma233.blog.vo.article.LastCommentVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 评论
 * Created by mac_zly on 2017/4/26.
 *
 * @author kazma
 */
@RestController
public class CommentController {

    @Autowired
    private ICommentService commentService;

    /**
     * 提交评论
     *
     * @param comment       评论信息
     * @param bindingResult 数据校验
     * @return 返回成功或者失败
     */
    @PostMapping(value = "/comments")
    public BaseResult commit(@RequestBody @Validated Comment comment, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        int res = commentService.insert(comment);

        return BaseResult.createResult(res);
    }

    /**
     * 获得某篇文章的评论
     *
     * @return 返回评论
     */
    @GetMapping(value = "/comments")
    public BaseResult<PageInfo> articleComment(CommentQueryVO commentQueryVO) {
        commentQueryVO.init();
        PageInfo pageInfo = commentService.queryComment(commentQueryVO);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    /**
     * 获得最近的50条评论
     */
    @GetMapping("/comments/last")
    public BaseResult lastComment() {
        List<CommentAndArticleVO> comments = commentService.queryLastComment(50);

        List<LastCommentVO> lastCommentVOS = new LinkedList<>();
        comments.forEach(comment -> {
            LastCommentVO lastComment = null;
            for (LastCommentVO lastCommentVO : lastCommentVOS) {
                if (lastCommentVO.getTitle().equals(comment.getArticleTitle())) {
                    lastComment = lastCommentVO;
                }
            }

            if (lastComment == null) {
                lastComment = new LastCommentVO();
                lastComment.setTitle(comment.getArticleTitle());
                lastComment.setComments(new LinkedList<>());
                lastCommentVOS.add(lastComment);
            }

            lastComment.getComments().add(comment);

        });

        return BaseResult.createResult(BaseResult.SUCCESS, lastCommentVOS);
    }

}
