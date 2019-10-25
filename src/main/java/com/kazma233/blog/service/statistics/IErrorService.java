package com.kazma233.blog.service.statistics;

import com.kazma233.blog.entity.statistics.vo.MongoErrorAdd;

public interface IErrorService {

    void save(MongoErrorAdd mongoErrorAdd);

}

