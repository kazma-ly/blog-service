package com.kazma233.blog.dao.article;


import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.vo.CommentArticleQuery;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import com.kazma233.blog.entity.comment.vo.CommentUpdate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    @Insert("insert into blog_comment_table (id, article_id, refer_id, content, create_time, nickname, email, net, reger_origin_id, uid, status) " +
            "values (#{id}, #{articleId}, #{referId}, #{content}, #{createTime}, #{nickname}, #{email}, #{net}, #{regerOriginId}, #{uid}, #{status})")
    void insert(Comment comment);

    List<CommentArticleTitleVO> queryAllAndArticleTitleByArgs(CommentQuery commentQuery);

    Long queryAllAndArticleTitleByArgsSize(CommentQuery commentQuery);

    @Select("SELECT * FROM blog_comment_table WHERE article_id = #{articleId} AND status = 'SHOW' ORDER BY create_time DESC limit #{limit} offset #{offset}")
    List<Comment> queryAllShowByArticleId(CommentArticleQuery commentArticleQuery);

    @Select("SELECT count(*) FROM blog_comment_table WHERE article_id = #{articleId} AND status = 'SHOW'")
    Long queryAllShowByArticleIdSize(CommentArticleQuery commentArticleQuery);

    @Update("UPDATE blog_comment_table SET status = #{status} WHERE id = #{id}")
    void updateStatus(CommentUpdate commentUpdate);
}