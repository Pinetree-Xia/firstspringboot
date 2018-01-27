package com.example.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.demo.dao.CoinTradeDao;
import com.example.demo.param.QueryCoinTradeParam;
import com.example.demo.po.CoinTradePO;
import com.example.demo.po.CoinTradeSummaryPerDay;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CoinTradeController {

    private static final String DATE_MINUTE_PATTERN = "yyyyMMddHHmm";

    SimpleDateFormat sdf_minute = new SimpleDateFormat(DATE_MINUTE_PATTERN);

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    private static final String PARAMETER_KEY = "queryDate";

    private Logger logger = Logger.getLogger(CoinTradeController.class);

    @Resource()
    private CoinTradeDao coinTradeDao;


    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }

    @RequestMapping("listData")
    public String listData(@RequestParam(required = false) String queryDateStr, ModelMap modelMap) {

        Date queryDate = null;

        Map queryCondition = new HashMap<String, String>();

        if (!StringUtils.isEmpty(queryDateStr)) {
            // 查询指定日期的交易额
            queryCondition.put(PARAMETER_KEY, queryDateStr);
        } else {
            queryDateStr = sdf.format(new Date());
        }

        logger.info(queryCondition);
        List<CoinTradeSummaryPerDay> datePOList = coinTradeDao.queryCoinTradesByDay(queryCondition);
        modelMap.put("datePOList", datePOList);
        modelMap.put("curDate", queryDateStr);

        return "listData";
    }


    /**
     * 查询指定开始和结束日期之间的总的交易量
     *
     * @param modelMap
     * @return
     */

    @RequestMapping("queryBTC")
    @ResponseBody
    public String queryBTC(@RequestParam String start, @RequestParam(required = false) String end, ModelMap modelMap) {


        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf_minute.parse(start);
            if (StringUtils.isEmpty(end)) {
                endDate = new Date();
            } else {
                endDate = sdf_minute.parse(end);
            }
        } catch (ParseException e) {
            logger.error("input parameter is invalid, pls confirm ", e);
        }

        logger.info("start: " + startDate + ",endDate:" + endDate);

        QueryCoinTradeParam param = new QueryCoinTradeParam();
        param.setBeginTime(startDate);
        param.setEndTime(endDate);

        BigDecimal totalBTC = new BigDecimal(0);

        List<CoinTradePO> datePOList = coinTradeDao.queryCoinTrades(param);
        if (CollectionUtils.isEmpty(datePOList)) {
            //modelMap.put("btc", totalBTC);
            logger.info("data from db is empty");
        } else {
            logger.info("datePOList size: " + datePOList.size());
            for (CoinTradePO coinTrade : datePOList) {
                BigDecimal itemAmount = coinTrade.getAmount().multiply(coinTrade.getPrice());
                logger.info(itemAmount.toString());
                totalBTC = totalBTC.add(itemAmount);
            }
        }

        //return result
        Map resultMap = new HashMap<String, String>();
        resultMap.put("start", sdf_minute.format(startDate));
        resultMap.put("end", sdf_minute.format(endDate));
        resultMap.put("btc", totalBTC);

        return JSON.toJSONString(resultMap);
    }


}
