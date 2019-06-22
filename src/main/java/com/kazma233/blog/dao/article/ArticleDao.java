package com.kazma233.blog.dao.article;

import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.vo.article.ArticleCategoryVO;
import com.kazma233.blog.vo.article.ArticleFull;
import com.kazma233.blog.vo.article.ArticleQueryVO;
import com.kazma233.blog.vo.article.ArticleSimple;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {

    int insert(ArticleFull record);

    @Delete("delete from blog_article_table where id = #{id}")
    int deleteById(String id);

    Article selectById(String id);

    ArticleFull selectFullById(String id);

    int updateByIdSelective(ArticleFull record);

    int updateByPrimaryKey(ArticleFull record);

    List<ArticleCategoryVO> queryArticleByArgs(ArticleQueryVO articleQueryVO);

    @Select("select id, title, read_num, archive_date, create_time from blog_article_table where state = #{status} order by create_time desc")
    List<ArticleSimple> queryArticleSimple(String status);
}