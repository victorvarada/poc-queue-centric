package org.sales.pds.poc.poc_queue_centric.consumer;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.queue.CustomQueue;
import org.sales.pds.poc.poc_queue_centric.queue.TaskWrapper;
import org.sales.pds.poc.poc_queue_centric.queue.QueueManager;
import org.sales.pds.poc.poc_queue_centric.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer extends Thread implements ICustomCallback, ICustomConsumer<Task> {
	private Logger logger = LoggerFactory.getLogger(TaskConsumer.class);
	
	private CustomQueue customQueue;
	private QueueManager queueManager;
	private int availableWorker = 4;
	private int checkTick = 3000;

	/**
	 * take a task and assign it to a worker
	 */
	public void consume() {
		TaskWrapper taskWrapper =  customQueue.getNextTask();
		assignTask(taskWrapper.getId(), taskWrapper.getTask());
	}

	public void run() {
		while (true) {
			if ( TaskConsumer.this.availableWorker > 0) {
				consume();
			} else {
				logger.info("Any worker available, i'm sleeping");
				try {
					sleep(checkTick);
				} catch (InterruptedException e) {
					logger.warn(e.getStackTrace().toString());
				}
			}
		}
	}

	public synchronized void call(long id) {
		customQueue.removeTaskFromMap(id);
		availableWorker++;
	}

	private synchronized void assignTask(long id, Task task) {
		availableWorker--;
		Worker w = new Worker(this);
		w.setTask(task);
		w.setId(id);
		w.start();
	}

	public QueueManager getQueueManager() {
		return queueManager;
	}
	
	public void setQueueManager(QueueManager taskScheduler) {
		this.queueManager = taskScheduler;
	}

}
