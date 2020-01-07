package com.kazma233.blog.service.statistics.impl;

import com.kazma233.blog.dao.statistics.VisitDao;
import com.kazma233.blog.entity.statistics.Visit;
import com.kazma233.blog.entity.statistics.vo.VisitGroupDay;
import com.kazma233.blog.service.statistics.IVisitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class VisitService implements IVisitService {

    private VisitDao visitDao;
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Visit visit) {
        visit.setDate(visit.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
        visitDao.insert(visit);
    }

    @Override
    public List<VisitGroupDay> groupVisitByDay() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.plusMonths(-1);

        List<VisitGroupDay> mappedResults = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("createTime").gte(lastMonth).lte(today)),
                        Aggregation.group("createTime").count().as("visitCount")
                ),
                "visit",
                VisitGroupDay.class
        ).getMappedResults();

        return mappedResults;
    }
}
