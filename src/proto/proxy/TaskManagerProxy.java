package proto.proxy;

import proto.entity.Task;
import proto.queue.CustomQueue;

/**
 * Proxy pour soumettre des tâches à la queue
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
	 * Paramêtre la tâche à soumettre dans la file d'attente
	 * @param task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	public CustomQueue getCustomQueue() {
		return customQueue;
	}

	/**
	 * Paramêtre la file à qui soumettre les tasks, ici on donne la référence de l'objet directement
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
			System.out.println(getClass().getName() + ": je soumets une tâche dans la file.");
			customQueue.addTask(task);
		}
	}
}
