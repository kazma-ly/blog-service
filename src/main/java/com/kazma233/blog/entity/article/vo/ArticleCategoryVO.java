package com.kazma233.blog.entity.article.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleCategoryVO {

    private String id;

    private String title;

    private String subtitle;

    private String categoryName;

    private String tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
