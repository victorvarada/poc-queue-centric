package org.sales.pds.poc.poc_queue_centric.worker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskCallback;
import org.sales.pds.poc.poc_queue_centric.interfaces.RemoteWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends UnicastRemoteObject implements Runnable, RemoteWorker {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(Worker.class);
	private Task task;
	private boolean available;
	private ITaskCallback callback;
	private long id;
	private UUID me;
	private int jobDuration = 10000;
	
	public Worker() throws RemoteException {
		this.setCallback(callback);
		me = UUID.randomUUID();
		available = true;
	}

	public void doJob(ITaskCallback callback) {
		logger.info(me + ", je commence: " + task.getJobtype() + " - " + task.getWorkDetail());
		try {
			Thread.sleep(jobDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(me + ", j'ai fini ma tache.");
		// FIXME: callback marche pas
		try {
			callback.call(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(me + ", callback effectue.");
		setAvailable(true);
	}
	
	public void run() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 1099);
			registry.rebind(me.toString(), this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	public ITaskCallback getCallback() {
		return callback;
	}

	public void setCallback(ITaskCallback callback) {
		this.callback = callback;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void setBusy() throws RemoteException {
		this.available = false;
	}
}
