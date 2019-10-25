package com.kazma233.blog.entity.comment.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentUpdate {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "状态不能为空")
    private String status;

}
