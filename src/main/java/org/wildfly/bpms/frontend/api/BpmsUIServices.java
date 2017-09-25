package org.wildfly.bpms.frontend.api;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UIServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmsUIServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsUIServices.class);
	
	private KieServicesClient kieServicesClient;
	private String containerId;

	private BpmsUIServices() {
		// Empty constructor
	}
	
	public BpmsUIServices(String containerId, KieServicesClient client) {
		kieServicesClient = client;
		this.containerId = containerId;
	}
	
	protected KieServicesClient getKieServicesClient() {
		return kieServicesClient;
	}
	
	protected String getContainerId() {
		return containerId;
	}

	/**
     * Returns process form for given process id that resides in given container
     * @param processId  unique process id
     * @param language language that form should be filtered for
     * @return string representation (json or xml depending on client marshaling selection) of the process form
     */
    public String getProcessFormByLanguage(String processId, String language) {
    	logger.debug("Executing getProcessFormByLanguage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessForm(getContainerId(), processId, language);
    }

    /**
     * Returns process form for given process id that resides in given container - without filtering values by language
     * @param processId  unique process id
     * @return string representation (json or xml depending on client marshaling selection) of the process form
     */
    public String getProcessForm(String processId) {
    	logger.debug("Executing getProcessForm");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessForm(getContainerId(), processId);
    }

    /**
     * Returns task form for given task id that belongs to given container
     * @param taskId unique task id
     * @param language language that form should be filtered for
     * @return  string representation (json or xml depending on client marshaling selection) of the task form
     */
    public String getTaskFormByLanguage(Long taskId, String language) {
    	logger.debug("Executing getTaskFormByLanguage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getTaskForm(getContainerId(), taskId, language);
    }

    /**
     * Returns task form for given task id that belongs to given container as raw content - without filtering values by language
     * @param taskId unique task id
     * @return  string representation (json or xml depending on client marshaling selection) of the task form
     */
    public String getTaskForm(Long taskId) {
    	logger.debug("Executing getTaskForm");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getTaskForm(getContainerId(), taskId);
    }

    /**
     * Returns process image (svg) of the given process id that belongs to given container
     * @param processId  unique process id
     * @return svg (xml) representing process image
     */
    public String getProcessImage(String processId) {
    	logger.debug("Executing getProcessImage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessImage(getContainerId(), processId);
    }

    /**
     * Returns process image (svg) with annotated active and completed nodes for given process instance
     * that belongs to given container
     * @param processInstanceId unique process instance id
     * @return svg (xml) representing process image annotated with active (in red) and completed (in grey) nodes
     */
    public String getProcessInstanceImage(Long processInstanceId) {
    	logger.debug("Executing getProcessInstanceImage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessInstanceImage(getContainerId(), processInstanceId);
    }
}
