package com.kazma233.blog.entity.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo<T> implements Serializable {

    private Integer pageNo;
    private Integer pageSize;
    private Integer pageTotal;
    private Long total;
    private List<T> data;

}
