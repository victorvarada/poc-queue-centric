package org.sales.pds.poc.poc_queue_centric.queue;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author JY2015, QGEFF
 *
 */
public class TaskProducer {
	private static Logger logger = LoggerFactory.getLogger(TaskProducer.class);

	private QueueManager queueManager;

	public TaskProducer(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	public QueueManager getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	public void submitTask(Task task) {

		if (task == null)
			throw new NullPointerException();

		logger.info("i submit a task in queue");
		queueManager.addTask(task);
	}
}
