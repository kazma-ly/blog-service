package com.kazma233.blog.controller.article;

import com.kazma233.blog.entity.dto.BaseResult;
import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.enums.ResultEnums;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.utils.ValidatedUtils;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import com.kazma233.blog.vo.article.RecentCommentVO;
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

    @PostMapping(value = "/comments")
    public BaseResult commit(@RequestBody @Validated Comment comment, BindingResult bindingResult) {
        ValidatedUtils.checkFieldErrors(bindingResult.getFieldErrors());
        commentService.insert(comment);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, null);
    }

    @GetMapping(value = "/comments")
    public BaseResult<PageInfo> articleComment(CommentQueryVO commentQueryVO) {
        commentQueryVO.init();
        PageInfo pageInfo = commentService.queryComment(commentQueryVO);

        return BaseResult.createSuccessResult(ResultEnums.SUCCESS, pageInfo);
    }

    @GetMapping("/comments/recent")
    public BaseResult recentComment() {
        List<CommentAndArticleVO> recentComments = commentService.queryRecentlyComment(50);

        List<RecentCommentVO> recentCommentVOS = new LinkedList<>();
        recentComments.forEach(comment -> {
            
            RecentCommentVO recentComment = recentCommentVOS.stream().
                    filter(rc -> rc.getTitle().equals(comment.getArticleTitle())).
                    findFirst().
                    orElseGet(() -> {
                        RecentCommentVO rcVO = RecentCommentVO.builder().
                                title(comment.getArticleTitle()).
                                comments(new LinkedList<>()).
                                build();
                        recentCommentVOS.add(rcVO);
                        return rcVO;
                    });

            recentComment.getComments().add(comment);
        });

        return BaseResult.createResult(BaseResult.SUCCESS, recentCommentVOS);
    }

}
