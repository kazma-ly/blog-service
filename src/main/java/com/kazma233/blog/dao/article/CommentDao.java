package com.kazma233.blog.dao.article;


import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.vo.article.CommentAndArticleVO;
import com.kazma233.blog.vo.article.CommentQueryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    @Insert("insert into blog_comment_table (id, article_id, refer_id, content, create_time, nickname, email, net, reger_origin_id) " +
            "values (#{id}, #{articleId}, #{referId}, #{content}, #{createTime}, #{nickname}, #{email}, #{net}, #{regerOriginId})")
    int insert(Comment comment);

    @Delete("DELETE FROM blog_comment_table WHERE id = #{id}")
    int deleteById(String id);

    List<CommentAndArticleVO> findByArgsHasTitle(CommentQueryVO commentQueryVO);

    List<Comment> findByArgs(CommentQueryVO commentQueryVO);
}