package com.kazma233.blog.service.article;


import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICommentService {

    void insert(Comment comment);

    Integer deleteById(String cid);

    PageInfo<CommentAndArticleVO> queryAllCommentAndArticleTitle(CommentQueryVO commentQueryVO);

    PageInfo<Comment> queryComment(CommentQueryVO commentQueryVO);

    List<CommentAndArticleVO> queryRecentlyComment(int num);
}
