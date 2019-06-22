package com.kazma233.blog.vo.article;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zly
 * @date 2019/1/4
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQueryVO extends QueryVO {

    @NotBlank(message = "文章id不能为空")
    private String articleId;

    private String content;

}
