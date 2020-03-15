package com.kazma233.blog.entity.common.vo;

import lombok.Data;

@Data
abstract public class DBQueryAbstract {

    private Integer limit = 10;
    private Integer offset = 0;

}
