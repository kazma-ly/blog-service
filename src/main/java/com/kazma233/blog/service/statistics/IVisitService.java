package com.kazma233.blog.service.statistics;

import com.kazma233.blog.entity.statistics.Visit;
import com.kazma233.blog.entity.statistics.vo.VisitGroupDay;

import java.util.List;

public interface IVisitService {

    void save(Visit visit);

    List<VisitGroupDay> groupVisitByDay();
}
