package com.kazma233.blog.entity.article.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleAdd {

    @NotBlank(message = "文章内容不能为空")
    private String content;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "副标题不能为空")
    private String subtitle;

    @NotBlank(message = "文章状态为空")
    private String state;

    @NotBlank(message = "文章分类不能为空")
    private String categoryId;

    @NotBlank(message = "文章标签不能为空")
    private String tags;

}
