package org.sales.pds.poc.poc_queue_centric.bootstrap;

import java.rmi.RemoteException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.sales.pds.poc.poc_queue_centric.queue.QueueManager;
import org.sales.pds.poc.poc_queue_centric.queue.TaskConsumer;
import org.sales.pds.poc.poc_queue_centric.queue.TaskProducer;
import org.sales.pds.poc.poc_queue_centric.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class Bootstrap implements ServletContextListener {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	private QueueManager queueManager;
	
	/**
	 * start the simulation<br>
	 * architecture : proto.main --> proxy --(o-- proto.queue --o)-- proto.consumer --> proto.worker
	 * @param args
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		queueManager = QueueManager.getInstance();
		
		TaskConsumer taskConsumer = new TaskConsumer(queueManager);
		taskConsumer.start();
		queueManager.setConsumer(taskConsumer);
		Worker worker;
		try {
			worker = new Worker(taskConsumer);
			new Thread(worker).start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TaskProducer taskProducer = new TaskProducer(queueManager);
		queueManager.setProducer(taskProducer);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
}
