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
	private Queue<TaskWrapper> queue;
	private Map<Long, TaskWrapper> taskMap;
	private QueueInfo queueInfo;
	
	public CustomQueue() {
		taskMap = new ConcurrentHashMap<Long, TaskWrapper>();
		queue = new DelayQueue<TaskWrapper>();
		queueInfo = new QueueInfo();
	}
	
	public void addTask(Task t) {
		TaskWrapper taskWrapper = new TaskWrapper(t);
		queue.add(taskWrapper);
		taskMap.put(taskWrapper.getId(), taskWrapper);
		
		updateQueueInfo();
	}
	
	public void removeTask(long id) {
		taskMap.remove(taskMap.get(id));
		updateQueueInfo();
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
}
