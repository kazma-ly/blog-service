package com.kazma233.blog.controller.manage.article;

import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.vo.CommentAndArticleVO;
import com.kazma233.blog.entity.article.vo.CommentQueryVO;
import com.kazma233.blog.entity.article.vo.RecentCommentVO;
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
    @GetMapping(value = "")
    public BaseResult all(@Validated CommentQueryVO commentQueryVO) {
        commentQueryVO.init();
        PageInfo<CommentAndArticleVO> commentArticlePage = commentService.queryAllCommentAndArticleTitle(commentQueryVO);

        return BaseResult.success(commentArticlePage);
    }

    @RequiresPermissions(value = {"manage", "admin"}, logical = Logical.OR)
    @DeleteMapping(value = "/{id}")
    public BaseResult deleteComment(@PathVariable("id") String id) {
        commentService.deleteById(id);

        return BaseResult.success();
    }

    @GetMapping("/recent")
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

        return BaseResult.success(recentCommentVOS);
    }

}
