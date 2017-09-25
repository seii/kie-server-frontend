package org.wildfly.bpms.frontend.api.console;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kie.server.api.model.instance.JobRequestInstance;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsJobServices;

public class BpmsJobConsole extends BpmsJobServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsJobConsole.class);

	public BpmsJobConsole(KieServicesClient client) {
		super(client);
	}
	
	public Long scheduleRequestConsole(String command, String date, String[] data, String containerId) {
		logger.debug("Entering scheduleRequestConsole");
		Long result = -1L;
		JobRequestInstance jobRequest;
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		Date startDate;
		try {
		    startDate = df.parse(date);
		    
		    Map<String, Object> newData = new HashMap<>(1);
		    for(int i = 0; i < data.length; i += 2) {
		    	newData.put(data[i], data[i+1]);
		    }
		  
		    //"data" is optional
			if(data.length > 0) {
				jobRequest = JobRequestInstance.builder().command(command).scheduledDate(startDate).data(newData).build();
			}else {
				jobRequest = JobRequestInstance.builder().command(command).scheduledDate(startDate).build();
			}
			
			logger.debug("Leaving scheduleRequestConsole");
			
			//"containerId" is optional
			if(containerId != null && !containerId.isEmpty()) {
				result = super.getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(containerId, jobRequest);
			}else {
				result = super.getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(jobRequest);
			}
		} catch (ParseException e) {
		    logger.error(e.getMessage());
		}
		
		return result;
	}

}
