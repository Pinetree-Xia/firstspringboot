package com.example.demo.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class CoinTrade {


    /**
     * 数据生成时间
     */
    private Date gmtCrate;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 日期
     */
    private Long date;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 订单ID
     */
    private Long tid;

    @Override
    public String toString() {
        return "CoinTrade{" +
                "gmtCrate=" + gmtCrate +
                ", tradeTime=" + tradeTime +
                ", price=" + price +
                ", amount=" + amount +
                ", tid=" + tid +
                '}';
    }

    public Date getGmtCrate() {
        return gmtCrate;
    }

    public void setGmtCrate(Date gmtCrate) {
        this.gmtCrate = gmtCrate;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}