package com.hehenian.scheduling.quartzweb;

public class JobDetailBean extends org.springframework.scheduling.quartz.JobDetailFactoryBean {

	private static final long serialVersionUID = 3366003525170771470L;
	
	private String[] jobDataKeys = new String[0];



	public String[] getJobDataKeys() {
		return jobDataKeys;
	}

	public void setJobDataKeys(String[] jobDataKeys) {
		this.jobDataKeys = jobDataKeys;
	}
}
