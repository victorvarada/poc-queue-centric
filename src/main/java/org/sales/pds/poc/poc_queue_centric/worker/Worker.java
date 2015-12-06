package org.sales.pds.poc.poc_queue_centric.worker;

import java.util.UUID;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskCallback;

public class Worker extends Thread {
	private Task task;
	private boolean available;
	private ITaskCallback callback;
	private long id;
	private UUID me;
	private int jobDuration = 10000;
	
	public Worker(ITaskCallback callback) {
		this.setCallback(callback);
		me = UUID.randomUUID();
	}
	
	public void doJob() {
		try {
			Thread.sleep(jobDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println(getClass().getName() + ": " + me + ", je commence: " + task.getJobtype() + " - " + task.getWorkDetail());
		doJob();
		System.out.println(getClass().getName() + ": " + me + ", j'ai fini ma t�che.");
		callback.call(id);
		System.out.println(getClass().getName() + ": " + me + ", callback effectu�. Fin du Thread.");
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public ITaskCallback getCallback() {
		return callback;
	}

	public void setCallback(ITaskCallback callback) {
		this.callback = callback;
	}

	public void setId(long id) {
		this.id = id;
	}
}
