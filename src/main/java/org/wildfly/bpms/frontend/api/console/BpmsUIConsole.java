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
     * Returns process image (svg) of the given process id that belongs to given container
     * @param processId  unique process id
     * @return svg (xml) representing process image
     */
    public String getProcessImageConsole(String processId) {
    	logger.debug("Executing getProcessImage");
    	return getKieServicesClient().getServicesClient(UIServicesClient.class)
        		.getProcessImage(getContainerId(), processId);
    }

}
