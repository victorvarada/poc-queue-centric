package org.sales.pds.poc.poc_queue_centric.bootstrap;

import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.sales.pds.poc.poc_queue_centric.consumer.TaskConsumer;
import org.sales.pds.poc.poc_queue_centric.entity.JobTypes;
import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.proxy.TaskProducer;
import org.sales.pds.poc.poc_queue_centric.queue.CustomQueue;
import org.sales.pds.poc.poc_queue_centric.queue.QueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class Bootstrap implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	private CustomQueue customQueue;
	private QueueManager queueManager;
	private TaskConsumer customConsumer;
	
	/**
	 * start the simulation<br>
	 * architecture : proto.main --> proxy --(o-- proto.queue --o)-- proto.consumer --> proto.worker
	 * @param args
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		//init the queue
		customQueue = new CustomQueue();
		queueManager = new QueueManager(customQueue);
		
		//init the consumer
		customConsumer = new TaskConsumer();
		customConsumer.setQueueManager(queueManager);
		
		//simulateTaskSubmissions();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void simulateTaskSubmissions() {
		customConsumer.start();
		TaskProducer producer = new TaskProducer(customQueue);
		
		while (true) {
			Task task = new Task();
			task.setJobtype(JobTypes.NotifyJob);
			task.setWorkDetail("Notifier Tartempion de rappeler le client Dupont.");
			
			producer.submitTask(task);
			
			/**
			 * wait randomly between 5sec and 8sec before re-submit 
			 */
			try {
				Thread.sleep(5000 + new Random().nextInt(3000));
			} catch (InterruptedException e) {
				logger.warn(e.getStackTrace().toString());
			}
		}
	}
	



}
