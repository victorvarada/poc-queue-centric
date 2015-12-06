package org.sales.pds.poc.poc_queue_centric.worker;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.sales.pds.poc.poc_queue_centric.entity.Task;

public interface RemoteWorker extends Remote {
	public void doJob() throws RemoteException;
	public boolean isAvailable() throws RemoteException;
	public void setTask(Task task) throws RemoteException;
	public void setId(long id) throws RemoteException;
}