package com.kazma233.blog.vo.user;

import com.kazma233.blog.vo.QueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zly
 * @date 2019/2/28
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryVO extends QueryVO {

    private String roleName;

    private String description;

}
