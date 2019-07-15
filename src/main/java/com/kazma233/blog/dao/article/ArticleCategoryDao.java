package com.kazma233.blog.dao.article;


import com.kazma233.blog.entity.article.ArticleCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCategoryDao {

    @Insert("insert into blog_article_category (id, category_name, create_time, uid) " +
            "values (#{id}, #{categoryName}, #{createTime}, #{uid})")
    void insert(ArticleCategory record);

    @Delete("delete from blog_article_category where id = #{id}")
    void deleteById(String id);

    @Update("update blog_article_category set category_name = #{categoryName} where id = #{id}")
    void updateById(ArticleCategory record);

    @Select("select id, category_name, create_time from blog_article_category where id = #{id}")
    ArticleCategory selectById(String id);

    @Select("select id, category_name, create_time from blog_article_category where uid = #{uid}")
    List<ArticleCategory> queryAll(String uid);

    @Select("select count(*) from blog_article_table where category_id = #{id}")
    Integer countByArticleId(String id);
}