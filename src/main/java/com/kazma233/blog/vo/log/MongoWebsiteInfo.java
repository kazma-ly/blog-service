package com.kazma233.blog.vo.log;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MongoWebsiteInfo {

    private Long allApiRequestSize;
    private Integer allIpRequestSize;

}
