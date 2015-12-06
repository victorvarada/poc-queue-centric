package org.sales.pds.poc.poc_queue_centric.queue;

import java.util.ArrayList;
import java.util.List;

import org.sales.pds.poc.poc_queue_centric.entity.Task;

public class CustomQueue {
	static long nextId = 1;
	List<TaskContainer> taskContainers = null;
	
	public CustomQueue() {
		taskContainers = new ArrayList<TaskContainer>();
	}
	
	/**
	 * Place la t�che en file d'attente et lui attribue un id unique,
	 * FIXME: Remplacer l'ajout de t�che par un WS REST
	 * @param t
	 */
	public void addTask(Task t) {
		TaskContainer tc = new TaskContainer();
		tc.setId(nextId++);
		tc.setTask(t);
		tc.setRunning(false);
		taskContainers.add(tc);
		System.out.println(getClass().getName() + ": nouvelle t�che en file d'attente. Taille file = " + printQueueDetails());
	}
	
	private String printQueueDetails() {
		int inProgress = 0;
		int awaiting = 0;
		
		for (TaskContainer tc : taskContainers) {
			if (tc.isRunning() == true)
				inProgress++;
			else
				awaiting++;
		}
		
		String details = new String("[Awaiting = " +awaiting+ ", InProgress = " +inProgress+ "]");
		return details;
	}

	/**
	 * Marque une t�che comme �tant en cours d'ex�cution
	 * @param id
	 */
	public void setTaskIsRunning(long id) {
		for (TaskContainer tc : taskContainers) {
			if (tc.getId() == id) {
				tc.setRunning(true);
				return;
			}
		}
	}
	
	public List<TaskContainer> getTaskContainers() {
		return taskContainers;
	}
	
	/**
	 * Retirer un t�che termin�e de la file
	 * @param id
	 */
	public void endTask(long id) {
		for (TaskContainer tc : taskContainers) {
			if (tc.getId() == id) {
				taskContainers.remove(tc);
				System.out.println(getClass().getName() + ": t�che termin�e. Taille file = " + printQueueDetails());
				return;
			}
		}
	}
}
