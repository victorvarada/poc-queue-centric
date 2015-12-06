package org.sales.pds.poc.poc_queue_centric.queue;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskCallback;
import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskConsumer;
import org.sales.pds.poc.poc_queue_centric.interfaces.RemoteWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer implements Runnable, ITaskCallback, ITaskConsumer {
	private Logger logger = LoggerFactory.getLogger(TaskConsumer.class);
	private Registry registry;
	private QueueManager queueManager;
	private int nbAvailableWorker;
	private int checkTick;
	private int cbkPort;

	public TaskConsumer(QueueManager queueManager) throws RemoteException {
		this.cbkPort = getUnusedPort();
		this.queueManager = queueManager;
		this.nbAvailableWorker = 4;
		this.checkTick = 3000;
		try {
			setRegistry(LocateRegistry.createRegistry(1099));
			UnicastRemoteObject.exportObject(this, this.cbkPort);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getUnusedPort() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(0);
			s.close();
			logger.info("Callback port: " + s.getLocalPort());
			return s.getLocalPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public TaskConsumer(QueueManager queueManager, int nbAvailableWorker, int fakeTaskLonger) throws RemoteException {
		this.queueManager = queueManager;
		this.nbAvailableWorker = nbAvailableWorker;
		this.checkTick = fakeTaskLonger;
	}

	/**
	 * take a task and assign it to a worker
	 */
	public void consume() {
		TaskWrapper taskWrapper = queueManager.getNextTask();

		// TODO QGd: refactor assignTask() method give the TaskWrapper directly
		// as param
		assignTask(taskWrapper.getId(), taskWrapper.getTask());
	}

	public void run() {
		while (true) {
			if (this.nbAvailableWorker > 0) {
				consume();
			} else {
				logger.info("No worker available, i'm sleeping for " + checkTick + "ms");
				try {
					Thread.sleep(checkTick);
				} catch (InterruptedException e) {
					logger.warn(e.getStackTrace().toString());
				}
			}
		}
	}

	public synchronized void call(long id) {
		queueManager.removeTaskFromMap(id);
		nbAvailableWorker++;
	}

	private synchronized void assignTask(long id, Task task) {
		RemoteWorker w;
		String[] workerNames = null;
		try {
			workerNames = registry.list();
			for (String name : workerNames) {
				w = (RemoteWorker) registry.lookup(name);
				if (w.isAvailable() == true) {
					w.setTask(task);
					w.setId(id);
					TaskHandler jh = new TaskHandler(w, this);
					jh.start();
					nbAvailableWorker--;
					return;
				}
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}
}
