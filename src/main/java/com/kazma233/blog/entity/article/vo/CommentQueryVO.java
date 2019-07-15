package com.kazma233.blog.entity.article.vo;

import com.kazma233.blog.entity.common.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQueryVO extends QueryVO {

    @NotBlank(message = "文章id不能为空")
    private String articleId;

    private String content;

    private String uid;

}
