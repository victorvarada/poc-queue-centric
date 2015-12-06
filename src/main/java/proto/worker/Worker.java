package proto.worker;

import java.util.UUID;

import proto.consumer.ICustomCallback;
import proto.entity.Task;

public class Worker extends Thread {
	private Task task;
	private boolean available;
	private ICustomCallback callback;
	private long id;
	private UUID me;
	private int jobDuration = 10000;
	
	public Worker(ICustomCallback callback) {
		this.setCallback(callback);
		me = UUID.randomUUID();
	}
	
	public void doJob() {
		try {
			Thread.sleep(jobDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println(getClass().getName() + ": " + me + ", je commence: " + task.getJobtype() + " - " + task.getWorkDetail());
		doJob();
		System.out.println(getClass().getName() + ": " + me + ", j'ai fini ma tâche.");
		callback.call(id);
		System.out.println(getClass().getName() + ": " + me + ", callback effectué. Fin du Thread.");
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public ICustomCallback getCallback() {
		return callback;
	}

	public void setCallback(ICustomCallback callback) {
		this.callback = callback;
	}

	public void setId(long id) {
		this.id = id;
	}
}
