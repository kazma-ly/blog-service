package com.kazma233.blog.entity.log.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MongoWebsiteInfo {

    private Long allApiRequestSize;
    private Integer allIpRequestSize;

}
