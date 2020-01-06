package com.kazma233.blog.service.statistics.impl;

import com.kazma233.blog.dao.statistics.VisitDao;
import com.kazma233.blog.entity.statistics.Visit;
import com.kazma233.blog.service.statistics.IVisitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class VisitService implements IVisitService {

    private VisitDao visitDao;

    @Override
    public void save(Visit visit) {
        visitDao.insert(visit);
    }
}
