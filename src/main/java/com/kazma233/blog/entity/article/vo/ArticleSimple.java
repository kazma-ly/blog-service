package com.kazma233.blog.entity.article.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleSimple implements Serializable {

    private String id;
    private String title;
    @JsonIgnore
    private String archiveDate;
    private Integer readNum;

}
