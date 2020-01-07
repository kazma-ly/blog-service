package com.kazma233.blog.entity.statistics.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VisitGroupDay {

    private LocalDate createTime;
    private Long visitCount;

}
