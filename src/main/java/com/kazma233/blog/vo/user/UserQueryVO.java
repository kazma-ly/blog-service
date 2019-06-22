package com.kazma233.blog.vo.user;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zly
 * @date 2019/1/11
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryVO extends QueryVO {

    private String username;
    private Date startTime;
    private Date endTime;

}
