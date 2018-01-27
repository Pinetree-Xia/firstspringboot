package com.example.demo.param;

import lombok.Data;

import java.util.Date;

/**
 * 查询参数
 */
@Data
public class QueryCoinTradeParam {

    private Date beginTime;

    private Date endTime;


    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
