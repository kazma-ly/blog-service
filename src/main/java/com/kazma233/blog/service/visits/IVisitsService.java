package com.kazma233.blog.service.visits;

import com.kazma233.blog.entity.log.MongoLog;
import com.kazma233.blog.entity.log.vo.MongoLogQueryVO;
import com.kazma233.blog.entity.log.vo.MongoWebsiteInfo;
import org.springframework.data.domain.Page;

public interface IVisitsService {

    void insert(MongoLog mongoLog);

    Page findLogs(MongoLogQueryVO mongoLogQueryVO);

    MongoWebsiteInfo websiteInfo(MongoLogQueryVO mongoLogQueryVO);

}
