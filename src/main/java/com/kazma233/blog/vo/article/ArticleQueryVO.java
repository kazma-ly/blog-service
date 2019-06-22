package com.kazma233.blog.vo.article;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zly
 * @date 2019/1/4
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryVO extends QueryVO {

    /**
     * 文章标题
     */
    private String title;

    private String textState;

    private String archiveDate;

    private String categoryId;

    private String startTime;

    private String endTime;
}
