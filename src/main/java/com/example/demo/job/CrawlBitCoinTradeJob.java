package com.example.demo.job;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.example.demo.dao.CoinTradeDao;
import com.example.demo.domain.CoinTrade;
import com.example.demo.dto.CrawlResultPair;
import com.example.demo.po.CoinTradePO;
import com.example.demo.utils.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 抓取数据
 */
@Component
public class CrawlBitCoinTradeJob {

    private static final String CRAWL_URL = "https://localbitcoins.com/bitcoincharts/cny/trades.json";

    private static final String CRAWL_PARAMETER = "?since=";

    private static final Integer MAX_PAGE_SIZE = 500;

    private static final Integer INVALID_SAVE_COUNT = -1;

    private Logger logger = Logger.getLogger(CrawlBitCoinTradeJob.class);

    @Resource
    CoinTradeDao coinTradeDao;

    @Scheduled(cron = "0 */15 * * * ? ")
    public void executeCrawl() {

        logger.info("execute crawl data" + new Date());

        CrawlResultPair crawlRst = crawlOnePage(CRAWL_URL);

        while (crawlRst != null && crawlRst.getCountPerQuery() == MAX_PAGE_SIZE) {
            //爬并存 下一页
            crawlRst = crawlOnePage(CRAWL_URL + CRAWL_PARAMETER + crawlRst.getTidOfLastItem());
        }

        logger.info("execute crawl data end.  ");
    }


    /**
     * 爬一次页面, 并解析，持久化
     *
     * @param url
     * @return 返回 ：数据条数和最后一次的TID
     */
    private CrawlResultPair crawlOnePage(String url) {

        String rawData = doGet(url);

        logger.info(" data from bit coin:" + rawData );
        List<CoinTrade> tradeList = buildData(rawData);
        logger.info(" data from bit coin size:" + tradeList.size() );

        if (!CollectionUtils.isEmpty(tradeList)) {

            int saveCount = persistData(tradeList);
            if (INVALID_SAVE_COUNT == saveCount) {
                return null;
            }

            int crawlCountRecord = tradeList.size();
            Long lastItemTid = tradeList.get(crawlCountRecord - 1).getTid();
            CrawlResultPair pair = new CrawlResultPair(crawlCountRecord, lastItemTid);
            return pair;
        } else {
            logger.error("Got empty data when crawlOnePage");
            return null;
        }
    }

    /**
     * 抓取页面数据
     *
     * @param url
     * @return
     */
    private String doGet(String url) {

        String result = "";
        HttpGet httpRequst = new HttpGet(url);

        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);//其中HttpGet是HttpUriRequst的子类
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);//取出应答字符串
                // 一般来说都要删除多余的字符
                result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            } else {
                httpRequst.abort();
            }
        } catch (Exception e) {
            logger.error("Got error data. exception:" + e.getMessage());
        }
        return result;
    }


    /**
     * 解析数据
     *
     * @param rawData
     * @return
     */
    private List<CoinTrade> buildData(String rawData) {

        List<CoinTrade> tradeDataList = JSON.parseArray(rawData, CoinTrade.class);

        return tradeDataList;
    }

    /**
     * 存数据到DB
     *
     * @param dataList
     * @return
     */
    private int persistData(List<CoinTrade> dataList) {
        int dataCount = -1;

        if (CollectionUtils.isEmpty(dataList)) {
            return dataCount;
        }

        for (CoinTrade trade : dataList) {
            CoinTradePO tradePO = new CoinTradePO();
            try {
                BeanUtils.copyProperties(trade, tradePO);
                tradePO.setTradeTime(DateUtil.TimeStamp2Date(trade.getDate()));
                dataCount += coinTradeDao.saveCoinTrade(tradePO);
            } catch (Exception e) {
               logger.error("got error when insert data to DB", e);
            }
        }

        return dataCount;
    }

}
