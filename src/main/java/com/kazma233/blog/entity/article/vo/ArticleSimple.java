package com.kazma233.blog.entity.article.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于查询归档信息,不查询文章内容等
 */
@Data
public class ArticleSimple implements Serializable {

    private String id;
    private String title;
    private String archiveDate;
    private Integer readNum;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
