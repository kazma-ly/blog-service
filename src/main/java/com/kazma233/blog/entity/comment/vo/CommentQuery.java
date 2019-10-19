package com.kazma233.blog.entity.comment.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQuery extends Query {

    @NotBlank(message = "文章id不能为空")
    private String articleId;

    private String content;

    @JsonIgnore
    private String uid;

}
