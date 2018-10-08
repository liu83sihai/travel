package com.dce.business.service.impl.trade;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dce.business.common.enums.KLineTypeEnum;
import com.dce.business.common.redis.JedisClient;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.KLineUtil;
import com.dce.business.common.util.SpringBeanUtil;
import com.dce.business.dao.trade.IKLineDao;
import com.dce.business.entity.trade.KLineDo;
import com.dce.business.entity.trade.MADo;
import com.dce.business.service.trade.IKLineService;

@Service("kLineService")
public class KLineServiceImpl implements IKLineService {
    private final static Logger logger = Logger.getLogger(KLineServiceImpl.class);
    private final static String KLINE_PREFIX = "kline_";
    private final static int count = 180; //只计算180个周期

    @Resource
    private IKLineDao kLineDao;

    @Override
    public List<KLineDo> getKLine(String type) {
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
        String json = jedisClient.getString(KLINE_PREFIX + type);
        List<KLineDo> klineList = null;
        //还没有K线图，需要计算
        if (StringUtils.isEmpty(json)) {
            klineList = calKLine(KLineTypeEnum.getKLineType(type));
            jedisClient.setString(KLINE_PREFIX + type, JSON.toJSONString(klineList), -1);
        } else {
            klineList = JSON.parseObject(json, new TypeReference<List<KLineDo>>() {
            });
        }

        return klineList;
    }

    @Override
    public void updateKLine() {
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");

        //小时
        //List<KLineDo> klineList = calKLine(KLineTypeEnum.KLINE_TYPE_HOUR);
        //jedisClient.setString(KLINE_PREFIX + KLineTypeEnum.KLINE_TYPE_HOUR.getType(), JSON.toJSONString(klineList), -1);

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0) {
            //天
            List<KLineDo> klineList = calKLine(KLineTypeEnum.KLINE_TYPE_DAY);
            jedisClient.setString(KLINE_PREFIX + KLineTypeEnum.KLINE_TYPE_DAY.getType(), JSON.toJSONString(klineList), -1);
            //周
            klineList = calKLine(KLineTypeEnum.KLINE_TYPE_WEEK);
            jedisClient.setString(KLINE_PREFIX + KLineTypeEnum.KLINE_TYPE_WEEK.getType(), JSON.toJSONString(klineList), -1);
        }
    }

    @Override
    public List<KLineDo> calKLine(KLineTypeEnum kLineType) {
        logger.info("开始计算K线图：" + kLineType);
        Assert.notNull(kLineType, "类型错误");
        Long time = System.currentTimeMillis();
        List<KLineDo> list = new ArrayList<>();

        Date terminationDate = DateUtil.getDate(new Date(), 0, 0, -1000); //最长只计算1000天之前的
        Date endDate = KLineUtil.getStartDate(kLineType);
        while (terminationDate.before(endDate) && (list.size() < count)) {
            Date startDate = KLineUtil.getNextDate(kLineType, endDate); //往前推时间

            //查询开始日期到结束日期以内最高价，最低价
            Map<String, Object> params = new HashMap<>();
            params.put("startDate", DateUtil.dateToString(startDate));
            params.put("endDate", DateUtil.dateToString(endDate));
            KLineDo volume = kLineDao.selectQty(params); //查询最高价、最低价、总交易量

            if (volume == null) {
                endDate = startDate;
                continue; //没有交易，不计算
            }

            params.put("open", 1);
            KLineDo open = kLineDao.selectPrice(params); //查询开盘价
            params.remove("open");
            params.put("close", 1);
            KLineDo close = kLineDao.selectPrice(params); //查询收盘价
            params.remove("close");

            KLineDo kLineDo = new KLineDo();
            kLineDo.setDate(DateUtil.klineDate(startDate, kLineType));
            kLineDo.setHigh(volume.getHigh());
            kLineDo.setLow(volume.getLow());
            kLineDo.setVolume(volume.getVolume());
            kLineDo.setOpen(open.getPrice());
            kLineDo.setClose(close.getPrice());
            kLineDo.setMa5(getMA(kLineType, endDate, 5));
            kLineDo.setMa10(getMA(kLineType, endDate, 10));
            kLineDo.setMa20(getMA(kLineType, endDate, 20));
            kLineDo.setKlineType(kLineType);
            list.add(kLineDo);

            endDate = startDate;
        }

        Collections.sort(list, new Comparator<KLineDo>() {
            @Override
            public int compare(KLineDo o1, KLineDo o2) {
                try {
                	KLineTypeEnum kLineType = o1.getKlineType();
                	
                    Date a = DateUtil.parseDate(o1.getDate(),kLineType);
                    Date b = DateUtil.parseDate(o2.getDate(),kLineType);
                    return a.compareTo(b);
                } catch (ParseException e) {
                    logger.error("排序错误：", e);
                }
                return 0;
            }
        });

        logger.info("计算K线图结束：" + kLineType + "; 总耗时ms：" + (System.currentTimeMillis() - time));
        return list;
    }

    private MADo getMA(KLineTypeEnum kLineType, Date endDate, int maNum) {
        Date startDate = KLineUtil.getMADate(kLineType, endDate, maNum);
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", DateUtil.dateToString(startDate));
        params.put("endDate", DateUtil.dateToString(endDate));
        return kLineDao.selectMA(params);
    }

}
