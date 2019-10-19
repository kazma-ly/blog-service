package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ArticleQuery extends Query {

    private String categoryId;

    private String uid;

}
