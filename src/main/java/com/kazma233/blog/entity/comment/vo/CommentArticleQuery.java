package com.kazma233.blog.entity.comment.vo;

import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentArticleQuery extends Query {

    private String articleId;

}
