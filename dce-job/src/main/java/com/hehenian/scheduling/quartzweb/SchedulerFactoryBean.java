package com.hehenian.scheduling.quartzweb;

/**
 * 任务调度管理
 */
public class SchedulerFactoryBean extends org.springframework.scheduling.quartz.SchedulerFactoryBean {

	public void setDisableDataSource(boolean disableDataSource) {
		if (disableDataSource) {
			super.setDataSource(null);
			super.setNonTransactionalDataSource(null);
		}
	}
}
