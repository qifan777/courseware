package com.jarcheng.mbg.common;

import java.util.List;

/**
 * ========================
 * 查询结果
 * Created with IntelliJ IDEA.

 */
public class QueryResult<T> {
    //数据列表
    private List<T> list;

    //数据总数
    private long total;

    public QueryResult(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public QueryResult() {}

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
