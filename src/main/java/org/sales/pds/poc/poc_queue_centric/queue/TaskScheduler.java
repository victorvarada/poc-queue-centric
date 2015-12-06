package org.sales.pds.poc.poc_queue_centric.queue;

import java.util.List;

// FIXME: R�fl�chir � remplacer le package proto.queue par un Queue Manager (peut �tre JQM ou ActiveMQ?) ou am�liorer le proto?
/**
 * Observe la file et d�termine quelle t�che doit �tre execut�e
 * @author JY2015
 *
 */
public class TaskScheduler {
	private CustomQueue customQueue;

	public CustomQueue getCustomQueue() {
		return customQueue;
	}

	/**
	 * Choisir la file � observer (inclure un pattern Observator et observer plusieurs files?)
	 * @param customeQueue
	 */
	public void setCustomQueue(CustomQueue customeQueue) {
		this.customQueue = customeQueue;
	}
	
	/**
	 * Determine la prochaine t�che � ex�cuter
	 * FIXME: Remplacer la d�termination de la prochaine t�che par un WS REST
	 * @return
	 */
	public TaskContainer getNextTask() {
		List<TaskContainer> taskContainers = customQueue.getTaskContainers();
		
		// FIXME: Am�liorer l'algo de d�termination de la prochaine t�che (deferred, priority...)
		for (TaskContainer tc: taskContainers) {
			if (tc.isRunning() == false) {
				customQueue.setTaskIsRunning(tc.getId());
				return (tc);
			}
		}
		return null;
	}
	
	/**
	 * Notifier le scheduler d'une t�che termin�e
	 * @param id
	 */
	public void endTask(long id) {
		customQueue.endTask(id);
	}
}
