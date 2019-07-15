package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.common.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ArticleQueryVO extends QueryVO {

    private String title;

    private String textState;

    private String archiveDate;

    private String categoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date endTime;

    private String uid;

}
