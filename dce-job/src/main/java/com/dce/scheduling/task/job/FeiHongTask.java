package com.dce.scheduling.task.job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.order.FeiHongOrder;
import com.dce.business.service.order.IFeiHongService;
import com.dce.business.service.order.IOrderService;

/** 
 * @author harry 分红任务
 * @date 2017年7月11日
 */
@Component
public class FeiHongTask extends QuartzJobBean  {
    private Log logger = LogFactory.getLog(FeiHongTask.class);
    
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IFeiHongService feiHongService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行分红定时任务");

        Map<String, Object> paraMap = new HashMap<String,Object>();
        paraMap.put("currentDate", DateUtils.ceiling(new Date(), Calendar.DAY_OF_MONTH));
        
		List<FeiHongOrder> fhOrderLst = orderService.selectFeiHongOrder(paraMap );
        for(FeiHongOrder fhOrder : fhOrderLst) {
        	try {
        		boolean canFeiHong =chkFeiHongDate(fhOrder);
        		if(canFeiHong == false) {
        			continue;
        		}
        		
        		feiHongService.doFeiHong(fhOrder);
        	}catch(Exception e) {
        		logger.error("分红出错:"+fhOrder);
        		logger.error(e);
        	}
        }
       
        logger.info("完成分红定时任务");
    }

    /**
	 * 	是否是能整除7
	 * @param fhorder
	 * @return
	 */
	private  boolean chkFeiHongDate(FeiHongOrder fhorder) {
		 Date startDate = DateUtils.ceiling(fhorder.getStartdate(), Calendar.DAY_OF_MONTH);
		 Date endDate = DateUtils.ceiling(new Date(), Calendar.DAY_OF_MONTH);
		 int diffDays = DateUtil.diffdays(startDate, endDate);
		return diffDays%7 == 0;
	}
	
    protected String getJobName() {
        return "分红定时任务";
    }
}
