package com.kazma233.blog.controller.statistics;

import com.kazma233.blog.entity.common.BaseResult;
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

    @GetMapping("/error/analysis")
    public BaseResult analysis() {

        return BaseResult.success();
    }

}
