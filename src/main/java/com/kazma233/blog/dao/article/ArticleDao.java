package com.kazma233.blog.dao.article;

import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.vo.ArticleCategoryVO;
import com.kazma233.blog.entity.article.vo.ArticleFull;
import com.kazma233.blog.entity.article.vo.ArticleQueryVO;
import com.kazma233.blog.entity.article.vo.ArticleSimple;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {

    void insert(ArticleFull record);

    @Delete("delete from blog_article_table where id = #{id}")
    void deleteById(String id);

    Article selectById(String id);

    ArticleFull selectFullById(String id);

    void updateByIdSelective(ArticleFull record);

    void updateByPrimaryKey(ArticleFull record);

    List<ArticleCategoryVO> queryArticleByArgs(ArticleQueryVO articleQueryVO);

    @Select("select id, title, read_num, archive_date, create_time from blog_article_table where state = #{status} order by create_time desc")
    List<ArticleSimple> queryArticleSimple(String status);
}