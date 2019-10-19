package com.kazma233.blog.entity.comment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentCommentVO {

    private String title;
    private List<CommentArticleTitleVO> comments;

}
