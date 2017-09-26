package org.wildfly.bpms.frontend.api.console;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.wildfly.bpms.frontend.api.BpmsJobServices;
import org.wildfly.bpms.frontend.constants.Constants;

public class BpmsJobConsole extends BpmsJobServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsJobConsole.class);

	public BpmsJobConsole(KieServicesClient client) {
		super(client);
	}
	
	public String scheduleRequestConsole(String command, String date, String[] data, String containerId) {
		logger.debug("Entering scheduleRequestConsole");
		Long resultId = -1L;
		String result = Constants.FAILED_TO_SCHEDULE;
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
				resultId = super.getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(containerId, jobRequest);
			}else {
				resultId = super.getKieServicesClient().getServicesClient(JobServicesClient.class).scheduleRequest(jobRequest);
			}
		} catch (ParseException e) {
		    logger.error(e.getMessage());
		}
		
		if(resultId != -1L) {
			result = String.format(Constants.SUCCESSFUL_SCHEDULE, String.valueOf(resultId));
		}
		
		return result;
	}
	
	public void cancelRequest(String requestId) {
		logger.debug("Executing cancelRequest");
		getKieServicesClient().getServicesClient(JobServicesClient.class).cancelRequest(Long.valueOf(requestId));
	}
	
	public void requeueRequest(String requestId) {
		logger.debug("Executing requeueRequest");
		getKieServicesClient().getServicesClient(JobServicesClient.class).requeueRequest(Long.valueOf(requestId));
	}

	public List<String> getRequestsByStatus(String page, String pageSize, String[] statuses) {
		logger.debug("Executing getRequestsByStatus");
		List<String> requestList = new ArrayList<>(1);
		for(int i = 0; i < statuses.length; i++) {
			requestList.add(statuses[i]);
		}
		List<RequestInfoInstance> instanceList = getKieServicesClient().getServicesClient(JobServicesClient.class)
				.getRequestsByStatus(requestList, Integer.valueOf(page), Integer.valueOf(pageSize));
		
		List<String> resultList = convertRequestInfoInstanceToString(instanceList, Constants.NO_REQUEST_FOUND_BY_STATUS);
		
		return resultList;
	}
	
	public List<String> getRequestsByBusinessKey(String businessKey, String page, String pageSize) {
		logger.debug("Executing getRequestsByBusinessKey");
		
		List<RequestInfoInstance> instanceList = getKieServicesClient().getServicesClient(JobServicesClient.class)
				.getRequestsByBusinessKey(businessKey, Integer.valueOf(page), Integer.valueOf(pageSize));
		List<String> requestList = convertRequestInfoInstanceToString(instanceList, Constants.NO_REQUEST_FOUND_BY_BUSINESS_KEY);
		
		return requestList;
	}
	
	public List<String> getRequestsByCommand(String command, String page, String pageSize) {
		logger.debug("Executing getRequestsByCommand");
		List<RequestInfoInstance> instanceList = getKieServicesClient().getServicesClient(JobServicesClient.class)
				.getRequestsByCommand(command, Integer.valueOf(page), Integer.valueOf(pageSize));

		List<String> requestList = convertRequestInfoInstanceToString(instanceList, Constants.NO_REQUEST_FOUND_BY_COMMAND);
		return requestList;
	}
	
	public RequestInfoInstance getRequestById(String requestId, String withErrors, String withData) {
		logger.debug("Executing getRequestById");
		return getKieServicesClient().getServicesClient(JobServicesClient.class).getRequestById(
				Long.valueOf(requestId), Boolean.valueOf(withErrors), Boolean.valueOf(withData));
	}
	
	private List<String> convertRequestInfoInstanceToString(List<RequestInfoInstance> instanceList, String errorMsg) {
		List<String> requestList = new ArrayList<>(1);
		
		if(instanceList != null && !instanceList.isEmpty()) {
			for(int i = 0; i < instanceList.size(); i++) {
				String addToList = String.format("=====%n");
				addToList = addToList.concat(String.format("Request ID: %s%n", String.valueOf(instanceList.get(i).getId())));
				addToList = addToList.concat(String.format("Status: %s%n", instanceList.get(i).getStatus()));
				addToList = addToList.concat(String.format("Business Key: %s%n", instanceList.get(i).getBusinessKey()));
				addToList = addToList.concat(String.format("Command Name: %s%n", instanceList.get(i).getCommandName()));
				requestList.add(addToList);
				
			}
		}else {
			requestList.add(errorMsg);
		}
		
		return requestList;
	}
}
