package com.kazma233.blog.entity.article.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ArticleSimple {

    private String id;
    private String title;
    @JsonIgnore
    private String archiveDate;
    private Integer readNum;

}
