package com.kazma233.blog.service.article;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.comment.vo.*;

import java.util.List;

public interface ICommentService {

    void commit(CommentAdd commentAdd);

    PageInfo<CommentArticleTitleVO> queryAllCommentAndArticleTitle(CommentQuery commentQuery);

    PageInfo queryArticleComment(CommentArticleQuery commentQuery);

    void updateStatues(CommentUpdate commentUpdate);
}
