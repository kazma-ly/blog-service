package com.kazma233.blog.entity.comment.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQuery extends Query {

    private String articleTitle;

    private String nickname;

    private String content;

    @JsonIgnore
    private String uid;

}
