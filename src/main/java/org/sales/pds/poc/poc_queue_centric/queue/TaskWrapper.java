package org.sales.pds.poc.poc_queue_centric.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.omg.PortableServer.portable.Delegate;
import org.sales.pds.poc.poc_queue_centric.entity.Task;

public class TaskWrapper implements Delayed {
	private Task task;
	private long id;
	private long startTime;
	private boolean running;
	
	public TaskWrapper(Task task) {
		this.task = task;
		this.startTime = System.currentTimeMillis() + task.getDelay();
	}
	
	@Override
	public int compareTo(Delayed task) {
		if (this.startTime < ((TaskWrapper) task).startTime) 
            return -1;
        
        if (this.startTime > ((TaskWrapper) task).startTime) 
            return 1;
        
        return 0;
	}
	
	@Override
	public long getDelay(TimeUnit unit) {
		long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
	
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
