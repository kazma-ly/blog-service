package com.kazma233.blog.service.article;


import com.kazma233.blog.entity.comment.vo.*;
import com.kazma233.blog.entity.common.PageInfo;

public interface ICommentService {

    void commit(CommentAdd commentAdd);

    PageInfo queryAllCommentAndArticleTitle(CommentQuery commentQuery);

    PageInfo queryArticleComment(CommentArticleQuery commentQuery);

    void updateStatues(CommentUpdate commentUpdate);
}
