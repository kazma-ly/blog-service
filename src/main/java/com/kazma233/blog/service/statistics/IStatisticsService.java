package com.kazma233.blog.service.statistics;

import com.kazma233.blog.entity.log.vo.MongoLogQueryVO;
import org.springframework.data.domain.Page;

public interface IStatisticsService {

    Page findLogs(MongoLogQueryVO mongoLogQueryVO);

}
