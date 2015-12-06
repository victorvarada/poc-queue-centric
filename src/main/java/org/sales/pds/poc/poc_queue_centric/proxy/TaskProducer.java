package org.sales.pds.poc.poc_queue_centric.proxy;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.queue.CustomQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author JY2015, QGEFF
 *
 */
public class TaskProducer {
	private static Logger logger = LoggerFactory.getLogger(TaskProducer.class);
	
	private CustomQueue customQueue;
	
	public TaskProducer(CustomQueue customQueue) {
		this.customQueue = customQueue;
	}

	public CustomQueue getCustomQueue() {
		return customQueue;
	}

	public void setCustomQueue(CustomQueue customQueue) {
		this.customQueue = customQueue;
	}

	public void submitTask(Task task) {
		if (task != null) {
			logger.info("i submit a task in queue");
			customQueue.addTask(task);
		} else {
			throw new NullPointerException();
		}
	}
}
