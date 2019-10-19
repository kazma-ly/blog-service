package com.kazma233.blog.entity.comment.vo;

import com.kazma233.blog.entity.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentArticleTitleVO extends Comment {

    private String articleTitle;

}
