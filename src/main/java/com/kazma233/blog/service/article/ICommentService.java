package com.kazma233.blog.service.article;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.vo.CommentAdd;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;

import java.util.List;

public interface ICommentService {

    void insert(CommentAdd commentAdd);

    void deleteById(String cid);

    PageInfo<CommentArticleTitleVO> queryAllCommentAndArticleTitle(CommentQuery commentQuery);

    List<CommentArticleTitleVO> queryRecentlyComment(int num);

    PageInfo queryArticleComment(CommentQuery commentQuery);
}
