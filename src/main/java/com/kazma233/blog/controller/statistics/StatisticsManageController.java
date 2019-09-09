package com.kazma233.blog.controller.statistics;

import com.kazma233.blog.entity.log.vo.MongoLogQueryVO;
import com.kazma233.blog.entity.result.BaseResult;
import com.kazma233.blog.service.visits.IVisitsService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/statistics")
@RequiresPermissions(value = {"admin"})
public class StatisticsManageController {

    private IVisitsService visitsService;

    @GetMapping("/")
    public BaseResult article(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();

        return BaseResult.success(visitsService.findLogs(mongoLogQueryVO));
    }

    @GetMapping("/website")
    public BaseResult webSiteInfo(MongoLogQueryVO mongoLogQueryVO) {
        mongoLogQueryVO.init();

        return BaseResult.success(visitsService.websiteInfo(mongoLogQueryVO));
    }

}
