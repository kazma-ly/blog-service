package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.article.Article;
import lombok.*;

/**
 * @author zly
 * @date 2019/1/4
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleFull extends Article {

    /**
     * 文章内容
     */
    private String content;

}
