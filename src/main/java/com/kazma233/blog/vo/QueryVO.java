package com.kazma233.blog.vo;

import lombok.Data;

/**
 * @author zly
 * @date 2019/1/24
 **/
@Data
abstract public class QueryVO {

    private Integer page;
    private Integer count;

    private Integer limit;
    private Integer offset;

    // 初始化
    public void init() {
        if (this.getPage() == null) {
            this.setPage(1);
        }
        if (this.getCount() == null) {
            this.setCount(10);
        }
    }

}
