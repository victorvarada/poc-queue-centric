package org.sales.pds.poc.poc_queue_centric.queue;

import java.util.Map;

public class QueueManager {
	private CustomQueue managedQueue;
	
	public QueueManager(){
	}
	public QueueManager(CustomQueue managedQueue) {
		super();
		this.managedQueue = managedQueue;
	}

	public TaskWrapper getNextTask() {
		Map<Long, TaskWrapper> taskMap = managedQueue.getTaskMap();

		for (TaskWrapper tc : taskMap.values()) {
			if (tc.isRunning() == false) {
				managedQueue.setTaskIsRunning(tc.getId());
				return tc;
			}
		}
		return null;
	}

	public void endTask(long id) {
		managedQueue.removeTask(id);
	}
	
	public CustomQueue getCustomQueue() {
		return managedQueue;
	}

	public void setCustomQueue(CustomQueue customeQueue) {
		this.managedQueue = customeQueue;
	}
}
