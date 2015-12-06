package org.sales.pds.poc.poc_queue_centric.queue;

public class QueueInfo {
	
	private int inProgress;
	private int awaiting;
	
	
	public int getInProgress() {
		return inProgress;
	}
	public void setInProgress(int inProgress) {
		this.inProgress = inProgress;
	}
	public int getAwaiting() {
		return awaiting;
	}
	public void setAwaiting(int awaiting) {
		this.awaiting = awaiting;
	}
	
	@Override
	public String toString() {
		return "[Awaiting = " +awaiting+ ", InProgress = " +inProgress+ "]";
	}
	
	
	
	
}
