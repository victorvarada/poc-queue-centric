package org.sales.pds.poc.poc_queue_centric.queue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME ) 
@Target( ElementType.METHOD )       
public @interface LogQueueInfo {

}
