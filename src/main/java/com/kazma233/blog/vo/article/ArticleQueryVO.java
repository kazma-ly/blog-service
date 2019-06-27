package com.kazma233.blog.vo.article;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class ArticleQueryVO extends QueryVO {

    private String title;

    private String textState;

    private String archiveDate;

    private String categoryId;

    private String startTime;

    private String endTime;
}
