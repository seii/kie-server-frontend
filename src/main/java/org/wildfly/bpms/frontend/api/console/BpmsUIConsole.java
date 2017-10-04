package org.wildfly.bpms.frontend.api.console;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UIServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsUIServices;

public class BpmsUIConsole extends BpmsUIServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsUIConsole.class);

	public BpmsUIConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}
	
	/**
     * Returns process form for given process id that resides in given container
     * @param processId  unique process id
     * @param language language that form should be filtered for
     * @return string representation (json or xml depending on client marshaling selection) of the process form
     */
    public String getProcessFormByLanguageConsole(String processId, String language) {
    	logger.debug("Executing getProcessFormByLanguage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessForm(getContainerId(), processId, language);
    }

    /**
     * Returns process form for given process id that resides in given container - without filtering values by language
     * @param processId  unique process id
     * @return string representation (json or xml depending on client marshaling selection) of the process form
     */
    public String getProcessFormConsole(String processId) {
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
    public String getTaskFormByLanguageConsole(String taskId, String language) {
    	logger.debug("Executing getTaskFormByLanguage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getTaskForm(getContainerId(), Long.valueOf(taskId), language);
    }

    /**
     * Returns task form for given task id that belongs to given container as raw content - without filtering values by language
     * @param taskId unique task id
     * @return  string representation (json or xml depending on client marshaling selection) of the task form
     */
    public String getTaskFormConsole(String taskId) {
    	logger.debug("Executing getTaskForm");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getTaskForm(getContainerId(), Long.valueOf(taskId));
    }

    /**
     * Returns process image (svg) of the given process id that belongs to given container
     * @param processId  unique process id
     * @return svg (xml) representing process image
     */
    public String getProcessImageConsole(String processId) {
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
    public String getProcessInstanceImageConsole(String processInstanceId) {
    	logger.debug("Executing getProcessInstanceImage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessInstanceImage(getContainerId(), Long.valueOf(processInstanceId));
    }

}
