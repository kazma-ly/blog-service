package com.kazma233.blog.dao.article;


import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    @Insert("insert into blog_comment_table (id, article_id, refer_id, content, create_time, nickname, email, net, reger_origin_id, uid) " +
            "values (#{id}, #{articleId}, #{referId}, #{content}, #{createTime}, #{nickname}, #{email}, #{net}, #{regerOriginId}, #{uid})")
    void insert(Comment comment);

    @Delete("DELETE FROM blog_comment_table WHERE id = #{id}")
    void deleteById(String id);

    List<CommentArticleTitleVO> findByArgsHasTitle(CommentQuery commentQuery);

    List<Comment> findByArgs(CommentQuery commentQuery);
}