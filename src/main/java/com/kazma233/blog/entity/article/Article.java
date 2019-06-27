package com.kazma233.blog.entity.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    @NotBlank(message = "id不能为空", groups = {UpdateGroup.class})
    private String id;

    @NotBlank(message = "标题不能为空", groups = {AddGroup.class})
    private String title;

    @NotBlank(message = "副标题不能为空", groups = {AddGroup.class})
    private String subtitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private Integer readNum;

    private String archiveDate;

    private String state;

    @NotBlank(message = "分类不能为空", groups = {AddGroup.class})
    private String categoryId;

    private String tags;

}