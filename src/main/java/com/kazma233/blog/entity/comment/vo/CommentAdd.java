package com.kazma233.blog.entity.comment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAdd {

    @NotBlank(message = "文章id不能为空")
    private String articleId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

    private String nickname;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱不正确")
    private String email;

    private String referId;

    private String net;

    private String regerOriginId;

}
