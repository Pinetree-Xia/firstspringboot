package com.example.demo.dto;

import lombok.Data;

@Data
public class CrawlResultPair {

    private int countPerQuery;

    private Long tidOfLastItem;

    public CrawlResultPair() {
    }

    public CrawlResultPair(int countPerQuery, Long tidOfLastItem) {
        this.countPerQuery = countPerQuery;
        this.tidOfLastItem = tidOfLastItem;
    }

    public int getCountPerQuery() {
        return countPerQuery;
    }

    public void setCountPerQuery(int countPerQuery) {
        this.countPerQuery = countPerQuery;
    }

    public Long getTidOfLastItem() {
        return tidOfLastItem;
    }

    public void setTidOfLastItem(Long tidOfLastItem) {
        this.tidOfLastItem = tidOfLastItem;
    }
}
