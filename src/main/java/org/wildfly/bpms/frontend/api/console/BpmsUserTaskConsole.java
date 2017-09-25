package org.wildfly.bpms.frontend.api.console;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.bpms.frontend.api.BpmsUserTaskServices;

public class BpmsUserTaskConsole extends BpmsUserTaskServices {
	
	private static Logger logger = LoggerFactory.getLogger(BpmsUserTaskConsole.class);

	public BpmsUserTaskConsole(String containerId, KieServicesClient client) {
		super(containerId, client);
	}
	
	public void activateTaskConsole(String taskId, String userId){
    	logger.debug("Executing activateTask");
    	Long taskIdLong = Long.valueOf(taskId);
    	getKieServicesClient().getServicesClient(UserTaskServicesClient.class)
        		.activateTask(getContainerId(), taskIdLong, userId);
    }

}
