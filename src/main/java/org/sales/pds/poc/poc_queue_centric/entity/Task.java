package org.sales.pds.poc.poc_queue_centric.entity;

import java.io.Serializable;

public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JobTypes jobtype;
	private String workDetail;
	private long delay;
	
	public Task(JobTypes jobtype, String workDetail, long delay) {
		this.jobtype = jobtype;
		this.workDetail = workDetail;
		this.delay = delay;
	}

	public JobTypes getJobtype() {
		return jobtype;
	}
	public void setJobtype(JobTypes jobtype) {
		this.jobtype = jobtype;
	}
	
	public String getWorkDetail() {
		return workDetail;
	}
	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
}
