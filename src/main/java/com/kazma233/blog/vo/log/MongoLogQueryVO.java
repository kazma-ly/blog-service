package com.kazma233.blog.vo.log;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zly
 * @date 2019/3/11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MongoLogQueryVO extends QueryVO {

    private Date start;
    private Date end;
    private String path;
    private String ip;

    @Override
    public void init() {
        super.init();
        if (path == null) {
            path = "";
        }
        if (ip == null) {
            ip = "";
        }
    }
}
