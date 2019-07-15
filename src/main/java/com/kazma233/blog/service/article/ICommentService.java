package com.kazma233.blog.service.article;


import com.github.pagehelper.PageInfo;
import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.entity.article.vo.CommentAndArticleVO;
import com.kazma233.blog.entity.article.vo.CommentQueryVO;

import java.util.List;

public interface ICommentService {

    void insert(Comment comment);

    void deleteById(String cid);

    PageInfo<CommentAndArticleVO> queryAllCommentAndArticleTitle(CommentQueryVO commentQueryVO);

    PageInfo<Comment> queryUserComment(CommentQueryVO commentQueryVO);

    List<CommentAndArticleVO> queryRecentlyComment(int num);

    PageInfo queryArticleComment(CommentQueryVO commentQueryVO);
}
