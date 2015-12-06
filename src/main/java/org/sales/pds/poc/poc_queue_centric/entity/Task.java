package org.sales.pds.poc.poc_queue_centric.entity;

public class Task {
	private JobTypes jobtype;
	private String workDetail;
	
	public JobTypes getJobtype() {
		return jobtype;
	}
	/**
	 * Choisir le type de job: Notify, Calculer les parts
	 * @param jobtype
	 */
	public void setJobtype(JobTypes jobtype) {
		this.jobtype = jobtype;
	}
	public String getWorkDetail() {
		return workDetail;
	}
	/**
	 * Infos relative � l'execution de la t�che en elle m�me (e.g. notifier qui? quel est le message?)
	 * @param workDetail
	 */
	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}
}
