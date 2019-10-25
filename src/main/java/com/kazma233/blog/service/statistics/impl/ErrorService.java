package com.kazma233.blog.service.statistics.impl;

import com.kazma233.blog.dao.statistics.ErrorDao;
import com.kazma233.blog.entity.statistics.MongoError;
import com.kazma233.blog.entity.statistics.vo.MongoErrorAdd;
import com.kazma233.blog.service.statistics.IErrorService;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ErrorService implements IErrorService {

    private ErrorDao errorDao;

    @Override
    public void save(MongoErrorAdd mongoErrorAdd) {
        try {
            MongoError mongoError = MongoError.builder().
                    id(Utils.generateID()).
                    code(mongoErrorAdd.getCode()).
                    message(mongoErrorAdd.getMessage()).
                    path(mongoErrorAdd.getPath()).
                    createTime(LocalDateTime.now()).
                    build();

            errorDao.insert(mongoError);
        } catch (Exception e) {
            log.error("record error: ", e);
        }
    }
}
