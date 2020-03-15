package com.kazma233.blog.utils.pageable;

import com.kazma233.blog.entity.common.PageInfo;
import com.kazma233.blog.entity.common.vo.Query;

import java.util.List;

public class PageableUtils {

    public static <T> PageInfo<T> pageInfo(Long total, Query query, List<T> data) {
        Integer pageSize = query.getPageSize();
        int pageTotal = (int) (total / pageSize);
        Integer calcPageTotal = total % pageSize == 0 ? pageTotal : pageTotal + 1;

        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPageNo(query.getPageNo());
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        pageInfo.setPageTotal(calcPageTotal);
        pageInfo.setData(data);

        return pageInfo;
    }

}
