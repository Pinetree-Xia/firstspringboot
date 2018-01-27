package com.example.demo.po;

import java.math.BigDecimal;

/**
 * 按日期汇总 交易额
 *
 */
public class CoinTradeSummaryPerDay {

    private String minute;

    private BigDecimal summerPerDay;

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public BigDecimal getSummerPerDay() {
        return summerPerDay;
    }

    public void setSummerPerDay(BigDecimal summerPerDay) {
        this.summerPerDay = summerPerDay;
    }
}
