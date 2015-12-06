package org.sales.pds.poc.poc_queue_centric.queue;

import org.sales.pds.poc.poc_queue_centric.entity.Task;

public class TaskContainer {
	private Task task;
	private long id;
	private boolean running;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
}
