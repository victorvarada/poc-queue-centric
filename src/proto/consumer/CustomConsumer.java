package proto.consumer;

import proto.entity.Task;
import proto.queue.TaskContainer;
import proto.queue.TaskScheduler;
import proto.worker.Worker;

public class CustomConsumer extends Thread implements ICustomCallback, ICustomConsumer<Task> {
	// FIXME: Remplacer l'objet TaskScheduler par son URL et faire un appel REST pour avoir la prochaine tâche
	private TaskScheduler taskScheduler;
	// FIXME: Upgrade availableWorker par un ThreadPoolExecutor
	private int availableWorker = 4;
	private int checkTick = 3000;

	/**
	 * Prend une tâche en attente et l'assigne à un worker.
	 */
	@Override
	public void consume() {
		TaskContainer taskContainer;
		taskContainer = taskScheduler.getNextTask();
		if (taskContainer != null) {
			System.out.println(getClass().getName() + ": tâche en attente à attribuer.");
			assignTask(taskContainer.getId(), taskContainer.getTask());
		} else {
			System.out.println(getClass().getName() + ": Pas de tâche en attente. Sleeping");
			try {
				sleep(checkTick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}

	public void run() {
		while (true) {
			if (getAvailableWorker() > 0) {
				consume();
			} else {
				System.out.println(getClass().getName() + ": Pas de worker disponible. Sleeping.");
				try {
					sleep(checkTick);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void call(long id) {
		taskScheduler.endTask(id);
		availableWorker++;
	}

	/**
	 * Assigne la tâche à un worker et lance le job
	 * 
	 * @param id
	 *            L'identifiant de tâche du TaskContainer
	 * @param task
	 *            La tâche
	 */
	private void assignTask(long id, Task task) {
		Worker w = new Worker(this);
		w.setTask(task);
		w.setId(id);
		w.start();
		availableWorker--;
	}

	private int getAvailableWorker() {
		return this.availableWorker;
	}

	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	/**
	 * Sélectionner le scheduler à requêter
	 * 
	 * @param taskScheduler
	 */
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

}
