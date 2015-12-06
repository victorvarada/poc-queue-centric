package org.sales.pds.poc.poc_queue_centric.proxy;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.queue.CustomQueue;

/**
 * Proxy pour soumettre des t�ches � la queue
 * 
 * @author JY2015
 *
 */
public class TaskManagerProxy {
	private Task task = null;
	private CustomQueue customQueue = null;

	public Task getTask() {
		return task;
	}

	/**
	 * Param�tre la t�che � soumettre dans la file d'attente
	 * @param task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	public CustomQueue getCustomQueue() {
		return customQueue;
	}

	/**
	 * Param�tre la file � qui soumettre les tasks, ici on donne la r�f�rence de l'objet directement
	 * mais dans le projet on donnera l'url REST pour soumettre une task
	 * 
	 * @param customQueue
	 */
	public void setCustomQueue(CustomQueue customQueue) {
		this.customQueue = customQueue;
	}

	/**
	 * Action d'envoyer la task dans la file d'attente, normalement fait l'appel REST
	 * FIXME: Faire un appel REST pour enqueue
	 */
	public void submitTask() {
		if (task != null && customQueue != null) {
			System.out.println(getClass().getName() + ": je soumets une t�che dans la file.");
			customQueue.addTask(task);
		}
	}
}
