package org.sales.pds.poc.poc_queue_centric.queue;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomQueue {
	private static Logger logger = LoggerFactory.getLogger(CustomQueue.class);
	
	private static long nextId = 1;
	private DelayQueue<TaskWrapper> queue;
	private QueueInfo queueInfo;
	private Map<Long, TaskWrapper> taskMap;
	
	public CustomQueue() {
		taskMap = new ConcurrentHashMap<Long, TaskWrapper>();
		queue = new DelayQueue<TaskWrapper>();
		queueInfo = new QueueInfo();
	}
	
	public void addTask(Task t) {
		TaskWrapper taskWrapper = new TaskWrapper(t);
		queue.offer(taskWrapper);
		taskMap.put(taskWrapper.getId(), taskWrapper);
		
		updateQueueInfo();
	}
	
	public void removeTaskFromMap(long id) {
		taskMap.remove(taskMap.get(id));
		updateQueueInfo();
	}
	
	public TaskWrapper getNextTask() {
		TaskWrapper taskWrapper;
		
		try {
			 taskWrapper = queue.take();
			 return taskWrapper;
		} catch (InterruptedException e) {
			logger.warn(e.getStackTrace().toString());
		}
		
		return null;
	}
	
	private void updateQueueInfo() {
		int inProgress = 0;
		int awaiting = 0;
		
		for (TaskWrapper taskWrapper : taskMap.values()) {
			if (taskWrapper.isRunning())
				inProgress++;
			else
				awaiting++;
		}
		
		queueInfo.setAwaiting(awaiting);
		queueInfo.setInProgress(inProgress);
		
		logger.info("Queue info {} ", queueInfo.toString());
	}

	public void setTaskIsRunning(long id) {
		taskMap.get(id).setRunning(true);
	}
	
	public Map<Long, TaskWrapper> getTaskMap() {
		return taskMap;
	}

	public static long getNextId() {
		return nextId;
	}

	public static void setNextId(long nextId) {
		CustomQueue.nextId = nextId;
	}

	public DelayQueue<TaskWrapper> getQueue() {
		return queue;
	}

	public void setQueue(DelayQueue<TaskWrapper> queue) {
		this.queue = queue;
	}

	public QueueInfo getQueueInfo() {
		return queueInfo;
	}

	public void setQueueInfo(QueueInfo queueInfo) {
		this.queueInfo = queueInfo;
	}

	public void setTaskMap(Map<Long, TaskWrapper> taskMap) {
		this.taskMap = taskMap;
	}
	
	
}
