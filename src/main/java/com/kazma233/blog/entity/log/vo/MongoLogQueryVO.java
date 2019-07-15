package com.kazma233.blog.entity.log.vo;

import com.kazma233.blog.entity.common.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zly
 * @date 2019/3/11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MongoLogQueryVO extends QueryVO {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
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
