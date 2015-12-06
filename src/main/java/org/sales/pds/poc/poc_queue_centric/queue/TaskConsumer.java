package org.sales.pds.poc.poc_queue_centric.queue;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskCallback;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskConsumer;
import org.sales.pds.poc.poc_queue_centric.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer extends Thread implements ITaskCallback, ITaskConsumer {
	private Logger logger = LoggerFactory.getLogger(TaskConsumer.class);
	
	private QueueManager customQueue;
	private int nbAvailableWorker;
	private int checkTick;

		
	public TaskConsumer(QueueManager customQueue) {
		this.customQueue = customQueue;
		this.nbAvailableWorker = 4;
		this.checkTick = 3000;
	}

	public TaskConsumer(QueueManager customQueue, int nbAvailableWorker, int fakeTaskLonger) {
		this.customQueue = customQueue;
		this.nbAvailableWorker = nbAvailableWorker;
		this.checkTick = fakeTaskLonger;
	}

	/**
	 * take a task and assign it to a worker
	 */
	public void consume() {
		TaskWrapper taskWrapper = customQueue.getNextTask();
		
		//TODO QGd: refactor assignTask() method give the TaskWrapper directly as param
		assignTask(taskWrapper.getId(), taskWrapper.getTask());
	}

	public void run() {
		while (true) {
			if ( TaskConsumer.this.nbAvailableWorker > 0) {
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
		nbAvailableWorker++;
	}

	private synchronized void assignTask(long id, Task task) {
		nbAvailableWorker--;
		Worker w = new Worker(this);
		w.setTask(task);
		w.setId(id);
		w.start();
	}

}
