package com.kazma233.blog.entity.common.vo;

abstract public class Query extends DBQueryAbstract {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        initDBQuery();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        initDBQuery();
    }

    @Override
    public String toString() {
        return "Query{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                "} " + super.toString();
    }

    void initDBQuery() {
        Integer pageSize = this.pageSize;
        this.setLimit(pageSize);
        this.setOffset((pageNo - 1) * pageSize);
    }

}
