package com.kazma233.blog.entity.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoErrorAdd {

    private Integer code;
    private String message;
    private String path;
    private String msg;

}
