package com.kazma233.blog.vo.article;

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
    private List<CommentAndArticleVO> comments;

}
