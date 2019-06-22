package com.kazma233.blog.vo.article;

import com.kazma233.blog.entity.article.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zly
 * @date 2019/1/4
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentAndArticleVO extends Comment {

    private String articleTitle;

}
