package proto.main;

import java.util.Random;

import proto.consumer.CustomConsumer;
import proto.entity.JobTypes;
import proto.entity.Task;
import proto.proxy.TaskManagerProxy;
import proto.queue.CustomQueue;
import proto.queue.TaskScheduler;

public class MyMain {
	CustomQueue customQueue;
	TaskScheduler taskScheduler;
	CustomConsumer customConsumer;
	
	private void simulateTaskSubmissions() {
		Random random = new Random();
		TaskManagerProxy tmProxy = new TaskManagerProxy();
		tmProxy.setCustomQueue(customQueue);
		
		while (true) {
			Task task = new Task();
			task.setJobtype(JobTypes.NotifyJob);
			task.setWorkDetail("Notifier Tartempion de rappeler le client Dupont.");
			tmProxy.setTask(task);
			if (random.nextInt(2) == 0)
				tmProxy.submitTask();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public MyMain() {
		// Mise en place de la file
		customQueue = new CustomQueue();
		taskScheduler = new TaskScheduler();
		taskScheduler.setCustomQueue(customQueue);
		
		// Démarrage du Consommateur de tâches
		customConsumer = new CustomConsumer();
		customConsumer.setTaskScheduler(taskScheduler);
		customConsumer.start();

		simulateTaskSubmissions();
	}
	
	/**
	 * Lancement de la simulation<br>
	 * architecture : proto.main --> proxy --(o-- proto.queue --o)-- proto.consumer --> proto.worker
	 * @param args
	 */
	public static void main(String[] args) {
		new MyMain();
	}

}
