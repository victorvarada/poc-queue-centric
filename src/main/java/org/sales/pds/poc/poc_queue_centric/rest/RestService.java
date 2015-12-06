package org.sales.pds.poc.poc_queue_centric.rest;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.sales.pds.poc.poc_queue_centric.entity.JobTypes;
import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.sales.pds.poc.poc_queue_centric.queue.QueueManager;
import org.sales.pds.poc.poc_queue_centric.queue.TaskConsumer;
import org.sales.pds.poc.poc_queue_centric.queue.TaskProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/task")
public class RestService {
	private static Logger logger = LoggerFactory.getLogger(RestService.class);
	
	@POST
	@Consumes("application/json")
	public Response submitTask(Task task) {
		String result = "Task submitted";
		
		QueueManager queueManager = QueueManager.getInstance();
		TaskProducer taskProducer = queueManager.getProducers().get(0);
		
		taskProducer.submitTask(task);
		logger.info("rest : task submitted");
		
		try {
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return Response.status(500).entity(result).build();
	}
	
	@GET
	public Response startSimulation(){
		
		new Thread(new MaSimulation()).start();
		
		return Response.status(200).entity("virtual simulation started").build();
	}
	
	
	private class MaSimulation implements Runnable{
		
		@Override
		public void run() {
			QueueManager queueManager = QueueManager.getInstance();
			TaskProducer taskProducer = queueManager.getProducers().get(0);
			
			while (true) {
				Task task = new Task(JobTypes.NotifyJob, "Notifier Tartempion de rappeler le client Dupont.", 0);
				taskProducer.submitTask(task);
				
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
	
}
