package proto.queue;

import java.util.List;

// FIXME: Réfléchir à remplacer le package proto.queue par un Queue Manager (peut être JQM ou ActiveMQ?) ou améliorer le proto?
/**
 * Observe la file et détermine quelle tâche doit être executée
 * @author JY2015
 *
 */
public class TaskScheduler {
	private CustomQueue customQueue;

	public CustomQueue getCustomQueue() {
		return customQueue;
	}

	/**
	 * Choisir la file à observer (inclure un pattern Observator et observer plusieurs files?)
	 * @param customeQueue
	 */
	public void setCustomQueue(CustomQueue customeQueue) {
		this.customQueue = customeQueue;
	}
	
	/**
	 * Determine la prochaine tâche à exécuter
	 * FIXME: Remplacer la détermination de la prochaine tâche par un WS REST
	 * @return
	 */
	public TaskContainer getNextTask() {
		List<TaskContainer> taskContainers = customQueue.getTaskContainers();
		
		// FIXME: Améliorer l'algo de détermination de la prochaine tâche (deferred, priority...)
		for (TaskContainer tc: taskContainers) {
			if (tc.isRunning() == false) {
				customQueue.setTaskIsRunning(tc.getId());
				return (tc);
			}
		}
		return null;
	}
	
	/**
	 * Notifier le scheduler d'une tâche terminée
	 * @param id
	 */
	public void endTask(long id) {
		customQueue.endTask(id);
	}
}
