package org.sales.pds.poc.poc_queue_centric.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITaskCallback extends Remote {
	public void call(long id) throws RemoteException;
}
