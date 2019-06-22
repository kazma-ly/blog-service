package com.kazma233.blog.vo.article;

import lombok.Data;

import java.util.List;

/**
 * @author zly
 * @date 2019/3/21
 **/
@Data
public class LastCommentVO {

    private String title;
    private List<CommentAndArticleVO> comments;

}
