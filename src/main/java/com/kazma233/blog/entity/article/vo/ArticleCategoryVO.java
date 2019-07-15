package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.article.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zly
 * @date 2019/1/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCategoryVO extends Article {

    private String categoryName;

}
