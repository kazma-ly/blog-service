package com.kazma233.blog.entity.article;

import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ArticleCategory {

    @NotBlank(message = "id不能为空", groups = UpdateGroup.class)
    private String id;

    @NotBlank(message = "分类名字不能为空", groups = AddGroup.class)
    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}