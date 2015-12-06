package org.sales.pds.poc.poc_queue_centric.bootstrap;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.sales.pds.poc.poc_queue_centric.rest.RestService;


public class BootstrapRESTService extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	
	public BootstrapRESTService() {
		CorsFilter corsFilter = new CorsFilter();
	    corsFilter.getAllowedOrigins().add("*");
	    
	    singletons.add(corsFilter);
		singletons.add(new RestService());
	}
	
	@Override
	public Set<Object> getSingletons(){
		return singletons;
	}
	
}