package com.kazma233.blog.entity.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 评论
 *
 * @author kazma
 */
@Data
public class Comment {

    private String id;

    @NotBlank(message = "文章id不能为空")
    private String articleId;

    private String referId;

    @NotBlank(message = "回复内容不能为空")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱不正确")
    private String email;

    private String net;

    private String regerOriginId;

    @JsonIgnore
    private String uid;

}