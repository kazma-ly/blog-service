package com.kazma233.blog.controller.statistics;

import com.kazma233.blog.entity.log.vo.MongoLogQueryVO;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.statistics.IStatisticsService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计控制器
 */
@AllArgsConstructor
@RestController
@RequestMapping("/statistics")
@RequiresPermissions(value = {"admin"})
public class StatisticsManageController {

    private IStatisticsService statisticsService;
    private MongoTemplate mongoTemplate;

    @GetMapping("/")
    public BaseResult article(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();

        return BaseResult.success(statisticsService.findLogs(mongoLogQueryVO));
    }

    @GetMapping("/website")
    public BaseResult webSiteInfo(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();

//        Long allViewSize = mongoLogDao.countAllByVisitTimeBetween(mongoLogQueryVO.getStart(), mongoLogQueryVO.getEnd());
//
//        Criteria criteria = Criteria.where("visitTime").gt(mongoLogQueryVO.getStart()).lt(mongoLogQueryVO.getEnd());
//        List<String> ips = mongoTemplate.findDistinct(new Query(criteria), "ip", MongoLog.class, String.class);
//
//        MongoWebsiteInfo websiteInfo = MongoWebsiteInfo.builder().allApiRequestSize(allViewSize).allIpRequestSize(ips.size()).build();

        return BaseResult.success();
    }
}
