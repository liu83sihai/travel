package com.dce.business.actions.trade;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.trade.KLineDo;
import com.dce.business.service.trade.IKLineService;

/** 
 * k线图 MA线 
 * @author parudy
 * @date 2018年3月29日 
 * @version v1.0
 */
@RestController
@RequestMapping("kline")
public class KLineController extends BaseController {
    private final static Logger logger = Logger.getLogger(KLineController.class);

    @Resource
    private IKLineService kLineService;

    @RequestMapping(value = "", method = {RequestMethod.GET,RequestMethod.POST})
    public Result<?> getKLine() {
        String type = getString("type");
        logger.info("查询K线图，type:" + type);

        Assert.hasText(type, "类型不能为空");
        List<KLineDo> list = kLineService.getKLine(type);

        return Result.successResult("查询成功", list);
    }

    /**   
     * K线定时任务，每一个小时跑一次
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void calKLine() {
        kLineService.updateKLine();
    }
}
