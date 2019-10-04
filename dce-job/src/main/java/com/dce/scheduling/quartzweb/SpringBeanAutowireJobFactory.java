package com.dce.scheduling.quartzweb;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * Job生成
 */
public class SpringBeanAutowireJobFactory extends AdaptableJobFactory implements
		ApplicationContextAware {

	private ApplicationContext spring;

	protected Object createJobInstance(TriggerFiredBundle bundle) {
		Object job = BeanUtils.instantiateClass(bundle.getJobDetail().getJobClass());
		spring.getAutowireCapableBeanFactory().autowireBeanProperties(job,
				AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
		return job;
	}

	public void setApplicationContext(ApplicationContext spring) throws BeansException {
		this.spring = spring;
	}
}
