package com.kazma233.blog.entity.common.vo;

import lombok.Data;

@Data
abstract public class Query {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private Integer limit;
    private Integer offset;

}
