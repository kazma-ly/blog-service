package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.common.vo.Query;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleBackendQuery extends Query {

    private String title;

    private String articleState;

    private String categoryId;

    private String year;

    private String uid;

}
