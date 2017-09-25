package org.wildfly.bpms.frontend.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.JobRequestInstance;
import org.kie.server.api.model.instance.RequestInfoInstance;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmsJobServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsJobServices.class);
	
	private KieServicesClient kieServicesClient;

	private BpmsJobServices() {
		// Empty constructor
	}
	
	public BpmsJobServices(KieServicesClient client) {
		kieServicesClient = client;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}

	public Long scheduleRequest(String command, Date date, Map<String, Object> data, String containerId) {
		logger.debug("Entering scheduleRequest");
		JobRequestInstance jobRequest;
		
		//"data" is optional
		if(data != null && data.isEmpty()) {
			jobRequest = JobRequestInstance.builder().command(command).scheduledDate(date).data(data).build();
		}else {
			jobRequest = JobRequestInstance.builder().command(command).scheduledDate(date).build();
		}
		
		logger.debug("Leaving scheduleRequest");
		
		//"containerId" is optional
		if(containerId != null && !containerId.isEmpty()) {
			return getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(containerId, jobRequest);
		}else {
			return getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(jobRequest);
		}
	}
	
	public void cancelRequest(long requestId) {
		logger.debug("Executing cancelRequest");
		getKieServicesClient().getServicesClient(JobServicesClient.class).cancelRequest(requestId);
	}
	
	public void requeueRequest(long requestId) {
		logger.debug("Executing requeueRequest");
		getKieServicesClient().getServicesClient(JobServicesClient.class).requeueRequest(requestId);
	}
	
	public List<RequestInfoInstance> getRequestsByStatus(List<String> statuses, Integer page, Integer pageSize) {
		logger.debug("Executing getRequestsByStatus");
		return getKieServicesClient().getServicesClient(JobServicesClient.class).getRequestsByStatus(statuses, page, pageSize);
	}
	
	public List<RequestInfoInstance> getRequestsByBusinessKey(String businessKey, Integer page, Integer pageSize) {
		logger.debug("Executing getRequestsByBusinessKey");
		return getKieServicesClient().getServicesClient(JobServicesClient.class).getRequestsByBusinessKey(businessKey, page, pageSize);
	}
	
	public List<RequestInfoInstance> getRequestsByCommand(String command, Integer page, Integer pageSize) {
		logger.debug("Executing getRequestsByCommand");
		return getKieServicesClient().getServicesClient(JobServicesClient.class).getRequestsByCommand(command, page, pageSize);
	}
	
	public RequestInfoInstance getRequestById(Long requestId, boolean withErrors, boolean withData) {
		logger.debug("Executing getRequestById");
		return getKieServicesClient().getServicesClient(JobServicesClient.class).getRequestById(requestId, withErrors, withData);
	}
}
