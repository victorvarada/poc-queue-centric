package org.sales.pds.poc.poc_queue_centric.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.sales.pds.poc.poc_queue_centric.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/task")
public class RestService {
	private static Logger logger = LoggerFactory.getLogger(RestService.class);
	
	@POST
	@Consumes("application/json")
	public Response submitTask(Task task) {
		String result = "Task submitted";
		try {
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return Response.status(500).entity(result).build();
	}
	
	
}
