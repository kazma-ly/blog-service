package com.kazma233.blog.dao.article;

import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.vo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {

    void insert(Article article);

    @Delete("delete from blog_article_table where id = #{id}")
    void deleteById(String id);

    Article findById(String id);

    Article selectAndContentById(String id);

    void updateByIdSelective(Article article);

    void updateByPrimaryKey(Article article);

    List<ArticleCategoryVO> queryPublishArticle(ArticleQuery articleQuery);

    List<ArticleCategoryBackendVO> queryArticle(ArticleBackendQuery articleBackendQuery);

    @Select("select id, title, read_num, archive_date from blog_article_table where state = #{status} order by create_time desc")
    List<ArticleSimple> queryArticleSimpleByStatus(String status);


}