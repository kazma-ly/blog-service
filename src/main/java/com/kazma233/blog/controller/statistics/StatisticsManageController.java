package com.kazma233.blog.controller.statistics;

import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.statistics.IVisitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsManageController {

    private IVisitService visitService;

    @GetMapping("/visit")
    public BaseResult analysis() {

        return BaseResult.success(visitService.groupVisitByDay());
    }

}
