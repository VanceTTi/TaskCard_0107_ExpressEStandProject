package com.fjt.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * page
 * 分页实体类
 * 用来包装分页数据的格式。
 */
public class ResultData<T> {
    //每次查询的数据集合：意思就是如果是第一页那么就放第一页的数据，点第二页那么就放第二页的数据
    private List<T> rows = new ArrayList<>();
    //总数量：一页5个数据，一共100也就是20页
    private  int total;
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
