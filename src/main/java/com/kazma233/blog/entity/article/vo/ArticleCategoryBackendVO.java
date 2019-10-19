package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.article.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCategoryBackendVO extends Article {

    private String categoryName;

}
