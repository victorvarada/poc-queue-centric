package org.sales.pds.poc.poc_queue_centric.consumer;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.queue.TaskWrapper;
import org.sales.pds.poc.poc_queue_centric.queue.QueueManager;
import org.sales.pds.poc.poc_queue_centric.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomConsumer extends Thread implements ICustomCallback, ICustomConsumer<Task> {
	private Logger logger = LoggerFactory.getLogger(CustomConsumer.class);
	
	private QueueManager taskScheduler;
	private int availableWorker = 4;
	private int checkTick = 3000;

	/**
	 * take a task and assign it to a worker
	 */
	public void consume() {
		TaskWrapper taskContainer = taskScheduler.getNextTask();
		if (taskContainer != null) {
			System.out.println(getClass().getName() + ": t�che en attente � attribuer.");
			assignTask(taskContainer.getId(), taskContainer.getTask());
		} else {
			System.out.println(getClass().getName() + ": Pas de t�che en attente. Sleeping");
			try {
				sleep(checkTick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (true) {
			if ( CustomConsumer.this.availableWorker > 0) {
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

	public void call(long id) {
		taskScheduler.endTask(id);
		availableWorker++;
	}

	/**
	 * assign task and start the worker
	 * 
	 * @param id
	 * @param task
	 * 
	 */
	private void assignTask(long id, Task task) {
		Worker w = new Worker(this);
		w.setTask(task);
		w.setId(id);
		w.start();
		availableWorker--;
	}

	public QueueManager getTaskScheduler() {
		return taskScheduler;
	}

	/**
	 * S�lectionner le scheduler � requ�ter
	 * 
	 * @param taskScheduler
	 */
	public void setTaskScheduler(QueueManager taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

}
