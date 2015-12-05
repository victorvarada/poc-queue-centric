package proto.consumer;

import proto.entity.Task;
import proto.queue.TaskContainer;
import proto.queue.TaskScheduler;
import proto.worker.Worker;

public class CustomConsumer extends Thread implements ICustomCallback, ICustomConsumer<Task> {
	// FIXME: Remplacer l'objet TaskScheduler par son URL et faire un appel REST pour avoir la prochaine t�che
	private TaskScheduler taskScheduler;
	// FIXME: Upgrade availableWorker par un ThreadPoolExecutor
	private int availableWorker = 4;
	private int checkTick = 3000;

	/**
	 * Prend une t�che en attente et l'assigne � un worker.
	 */
	@Override
	public void consume() {
		TaskContainer taskContainer;
		taskContainer = taskScheduler.getNextTask();
		if (taskContainer != null) {
			System.out.println(getClass().getName() + ": t�che en attente � attribuer.");
			assignTask(taskContainer.getId(), taskContainer.getTask());
		} else {
			System.out.println(getClass().getName() + ": Pas de t�che en attente. Sleeping");
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
	 * Assigne la t�che � un worker et lance le job
	 * 
	 * @param id
	 *            L'identifiant de t�che du TaskContainer
	 * @param task
	 *            La t�che
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
	 * S�lectionner le scheduler � requ�ter
	 * 
	 * @param taskScheduler
	 */
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

}
