package com.hehenian.scheduling.task.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/** 
 * @author harry
 * @date 2017年7月11日
 */
@Component
public class CapitalTask extends QuartzJobBean implements StatefulJob {
    private Log logger = LogFactory.getLog(CapitalTask.class);


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行额度检查定时任务");

       
        logger.info("完成额度检查定时任务");
    }

    protected String getJobName() {
        return "额度检查定时任务";
    }
}
