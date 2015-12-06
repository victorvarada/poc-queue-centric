package org.sales.pds.poc.poc_queue_centric.queue;

import java.rmi.RemoteException;

import org.sales.pds.poc.poc_queue_centric.interfaces.ITaskCallback;
import org.sales.pds.poc.poc_queue_centric.interfaces.RemoteWorker;

public class TaskHandler extends Thread {
	RemoteWorker w;
	ITaskCallback callback;

	public TaskHandler(RemoteWorker w, ITaskCallback callback) {
		this.w = w;
		this.callback = callback;
	}

	public void run() {
		try {
			this.w.doJob(this.callback);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
