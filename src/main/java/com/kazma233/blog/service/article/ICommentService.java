package com.kazma233.blog.service.article;


import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 评论Service
 * Created by mac_zly on 2017/4/26.
 *
 * @author kazma
 */

public interface ICommentService {

    /**
     * 提交评论
     *
     * @param comment 评论信息
     * @return 返回插入的条数 1 表示ok
     */
    int insert(Comment comment);

    /**
     * 通过id删除评论
     */
    Integer deleteById(String cid);

    /**
     * 查询某篇文章下的所有评论
     */
    PageInfo<CommentAndArticleVO> queryCommentAndArticleTitle(CommentQueryVO commentQueryVO);

    /**
     * 查询某篇文章下的所有评论, 没有文章标题
     */
    PageInfo<Comment> queryComment(CommentQueryVO commentQueryVO);

    /**
     * 查询最近的留言
     *
     * @param num 查询多少条
     * @return 返回留言列表
     */
    List<CommentAndArticleVO> queryLastComment(int num);
}
