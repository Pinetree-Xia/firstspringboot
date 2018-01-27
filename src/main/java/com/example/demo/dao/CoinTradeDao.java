package com.example.demo.dao;


import com.example.demo.param.QueryCoinTradeParam;
import com.example.demo.po.CoinTradePO;
import com.example.demo.po.CoinTradeSummaryPerDay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * table : coin_trade 数据访问层
 */
@Mapper
public interface CoinTradeDao {

    /**
     * 查询交易记录
     * @param queryParam
     * @return
     */
    List<CoinTradePO> queryCoinTrades(QueryCoinTradeParam queryParam);

    /**
     * 按日查询交易记录并汇总
     * @return
     */
    List<CoinTradeSummaryPerDay> queryCoinTradesByDay(Map<String, String> conditionMap);

    /**
     *
     * @param coinTradePO
     */
    Integer saveCoinTrade(CoinTradePO coinTradePO);

}
